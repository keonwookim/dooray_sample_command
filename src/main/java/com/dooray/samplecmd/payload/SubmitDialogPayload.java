package com.dooray.samplecmd.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmitDialogPayload {
    private String text;
    private String type;
    private String updateCmdToken;
    private String prevCmdToken;
    private Long channelId;
    private Long tenantId;
    private String channelName;
    private Long userId;
    private String command;
    private String appToken;
    private String tenantDomain;
    private String actionName;
    private String actionValue;
    private String callbackId;
    private String triggerId;
    private String responseUrl;
    private String cmdToken;
    private User user;
    private Tenant tenant;
    private Channel channel;
    private SlashCommandResponse originalMessage;
    private Map<String, String> submission;

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tenant {
        private Long id;
        private String domain;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Channel {
        private Long id;
        private String name;
    }
}
