package com.dooray.samplecmd.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class InteractiveRequest {
    private String commandName;
    private String command;
    private String text;
    private String callbackId;
    private String actionText;
    private String actionValue;
    private String appToken;
    private String cmdToken;
    private String triggerId;
    private String commandRequestUrl;
    private Long channelLogId;
    private User user;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private Long id;
        private String email;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tenant {
        private Long id;
        private String domain;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Channel {
        private Long id;
        private String name;
    }
}
