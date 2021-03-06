package com.spechpro.biometric.onepass.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 01.05.2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetStartSessionDto {

    @JsonProperty
    public String sessionId;

    @JsonProperty
    public String transactionId;

    @JsonCreator
    public GetStartSessionDto(@JsonProperty("session_id") String sessionId,
                              @JsonProperty("transaction_id") String transactionId) {

        this.sessionId = sessionId;
    }

}
