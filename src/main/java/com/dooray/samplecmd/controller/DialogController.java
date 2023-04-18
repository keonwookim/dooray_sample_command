package com.dooray.samplecmd.controller;

import com.dooray.samplecmd.payload.Dialog;
import com.dooray.samplecmd.payload.SlashCommandRequest;
import com.dooray.samplecmd.payload.SlashCommandResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

@RestController
public class DialogController {
    private final RestTemplate restTemplate;

    public DialogController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/api/dialog/open")
    public SlashCommandResponse openDialog(HttpServletRequest servletRequest,
                                           @RequestBody SlashCommandRequest request) throws MalformedURLException, JsonProcessingException {
        SlashCommandResponse dialogRequest = makeDialogPayload(request);
        requestOpenDialog(servletRequest, request, dialogRequest);

        return SlashCommandResponse.builder().build();
    }

    private SlashCommandResponse makeDialogPayload(SlashCommandRequest request) {
        Dialog.Element element = Dialog.Element.builder()
                .type("textarea")
                .label("Ask a question!")
                .name("question")
                .placeholder("Please write a question.")
                .optional(false)
                .build();
        Dialog dialog = Dialog.builder()
                .callbackId("dialog")
                .title("Ask to ChatGPT")
                .submitLabel("Send")
                .elements(Collections.singletonList(element))
                .build();
        return SlashCommandResponse.builder()
                .triggerId(request.getTriggerId())
                .dialog(dialog)
                .build();
    }

    private void requestOpenDialog(HttpServletRequest servletRequest, SlashCommandRequest request,
                           SlashCommandResponse dialogRequest) throws MalformedURLException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL(request.getResponseUrl());
        String domain = (url.getPort() != -1) ? url.getHost() + ":" + url.getPort() : url.getHost();
        String dialogUrl = "https://" + domain + "/messenger/api/channels/" + request.getChannelId() + "/dialogs";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dialogUrl);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Dooray-Db-Id", servletRequest.getHeader("Dooray-Db-Id"));
        httpHeaders.add("token", request.getCmdToken());

        HttpEntity<?> entity = new HttpEntity<>(mapper.writeValueAsString(dialogRequest), httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, String.class);
    }

}
