package com.dooray.samplecmd.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dialog {
    private String callbackId;
    private String title;
    private String submitLabel;
    private List<Element> elements;

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Element {
        private String type;
        private String subtype;
        private String label;
        private String name;
        private String value;
        private List<Option> options;
        private String dataSource;
        private String minLength;
        private String maxLength;
        private String placeholder;
        private String hint;
        private Boolean optional;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Option {
        private String label;
        private String value;
        private String text;
    }
}
