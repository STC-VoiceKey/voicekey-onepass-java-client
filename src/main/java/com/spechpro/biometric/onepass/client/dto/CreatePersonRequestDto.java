package com.spechpro.biometric.onepass.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sadurtinova on 13.09.2016.
 */
public class CreatePersonRequestDto {

    @JsonProperty("person_id")
    public String personId;

    public CreatePersonRequestDto(String personId){
        this.personId = personId;
    }
}
