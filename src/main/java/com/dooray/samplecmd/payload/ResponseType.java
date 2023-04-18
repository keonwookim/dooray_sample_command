package com.dooray.samplecmd.payload;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseType {
    EPHEMERAL("ephemeral"), IN_CHANNEL("inChannel");

    String value;

    ResponseType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

}
