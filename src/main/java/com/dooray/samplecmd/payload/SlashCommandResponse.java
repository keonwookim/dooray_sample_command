package com.dooray.samplecmd.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SlashCommandResponse {
    private Boolean replaceOriginal;
    private Boolean deleteOriginal;
    private String text;
    private ResponseType responseType;
    private List<Attachment> attachments;
    private Dialog dialog;
    private String triggerId;
    private Long channelId;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attachment{
        private String callbackId;
        private List<Action> actions;
        private String text;
        private String title;
        private String imageUrl;
        private List<Field> fields;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Action {
        private String name;
        private String type;
        private String text;
        private String value;
        private String style;
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Field {
        private String title;
        private String value;
        @JsonProperty("short")
        private Boolean shortFlag;
    }
}
