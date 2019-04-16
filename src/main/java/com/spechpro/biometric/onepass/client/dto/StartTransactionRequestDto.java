package com.spechpro.biometric.onepass.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 01.05.2018.
 */
public class StartTransactionRequestDto {

    @JsonProperty
    public String transactionId;

    @JsonCreator
    public StartTransactionRequestDto(@JsonProperty("transaction_id") String transactionId){
        this.transactionId = transactionId;
    }

}
