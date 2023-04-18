package com.dooray.samplecmd.controller;

import com.dooray.samplecmd.payload.ResponseType;
import com.dooray.samplecmd.payload.SlashCommandRequest;
import com.dooray.samplecmd.payload.SlashCommandResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class MessageController {
    @PostMapping("/api/message")
    public SlashCommandResponse sendMessage(@RequestBody SlashCommandRequest request) {
        return SlashCommandResponse.builder()
                .text("Send a test message")
                .replaceOriginal(false)
                .responseType(ResponseType.IN_CHANNEL)
                .attachments(Arrays.asList(SlashCommandResponse.Attachment.builder()
                                                   .callbackId("attachment-1")
                                                   .text("Attachment Text")
                                                   .title("Attachment Title")
                                                   .build(),
                                           SlashCommandResponse.Attachment.builder()
                                                   .callbackId("Field-1")
                                                   .fields(Arrays.asList(SlashCommandResponse.Field.builder()
                                                                   .title("Field title")
                                                                   .value("Field value")
                                                                   .shortFlag(false)
                                                                                 .build(),
                                                                         SlashCommandResponse.Field.builder()
                                                                                 .title("Field2 title")
                                                                                 .value("Field2 value")
                                                                                 .shortFlag(false)
                                                                                 .build())
                                                   ).build())
                ).build();
    }
}
