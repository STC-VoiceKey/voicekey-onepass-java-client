package com.spechpro.biometric.onepass.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 15.09.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartDynamicVerificationRequestDto {

        @JsonProperty
        public String password;

        @JsonProperty
        public String transactionId;

        @JsonCreator
        public StartDynamicVerificationRequestDto(@JsonProperty("password") String password,
                                                  @JsonProperty("transactionId") String transactionId) {
            this.password = password;
            this.transactionId = transactionId;
        }
}
