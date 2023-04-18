package com.dooray.samplecmd.controller;

import com.dooray.samplecmd.payload.ResponseType;
import com.dooray.samplecmd.payload.SlashCommandResponse;
import com.dooray.samplecmd.payload.SubmitDialogPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RestController
public class InteractiveController {
    private final RestTemplate restTemplate;

    public InteractiveController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/api/interaction")
    public SlashCommandResponse interactiveButton(HttpServletRequest request, @RequestBody SubmitDialogPayload requestPayload) throws JsonProcessingException {
        String answer = null;
        System.out.println(requestPayload);
        if (requestPayload.getType().equals("dialog_submission")) {
            String question = requestPayload.getSubmission().get("question");
            answer = requestGpt(question);

            StringBuilder responseText = new StringBuilder();
            Long tenantId = requestPayload.getTenant().getId();
            Long memberId = requestPayload.getUser().getId();
            String mentionText = "(dooray://" + tenantId + "/members/" + memberId + " \"member\")";
            responseText.append(mentionText + "'s ")
                    .append("Question: ")
                    .append(question)
                    .append("\n\n")
                    .append("Answer: ")
                    .append(answer);
            sendCommandHookMessage(request, requestPayload, responseText.toString());

            return SlashCommandResponse.builder()
                    .channelId(requestPayload.getChannel().getId())
                    .text(responseText.toString())
                    .replaceOriginal(false)
                    .responseType(ResponseType.IN_CHANNEL)
                    .build();
        }
        return SlashCommandResponse.builder().build();
    }

    private void sendCommandHookMessage(HttpServletRequest request, SubmitDialogPayload requestPayload,
                           String text) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestPayload.getResponseUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Dooray-Db-Id", request.getHeader("Dooray-Db-Id"));
        httpHeaders.add("token", requestPayload.getCmdToken());
        SlashCommandResponse response = SlashCommandResponse.builder()
                .channelId(requestPayload.getChannel().getId())
                .text(text)
                .replaceOriginal(false)
                .responseType(ResponseType.IN_CHANNEL)
                .build();
        HttpEntity<?> entity = new HttpEntity<>(mapper.writeValueAsString(response), httpHeaders);
        restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, String.class);
    }

    private String requestGpt(String question) {
        OpenAiService service = new OpenAiService("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", Duration.ZERO);
        ChatCompletionRequest completionRequest1 = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(new ChatMessage("user", question)))
                .build();
        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest1)
                .getChoices();
        if (!choices.isEmpty()) {
            return choices.get(0).getMessage().getContent();
        }
        return "";
    }
}
