package com.spechpro.biometric.onepass.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sadurtinova on 15.09.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetPersonResponseDto {
    @JsonProperty
    public boolean isFullEnroll;

    @JsonProperty
    public List<ModelDto> models;

    @JsonProperty
    public String id;

    @JsonProperty
    public boolean isDeleted;

    @JsonCreator
    public GetPersonResponseDto(@JsonProperty("is_full_enroll") boolean isFullEnroll,
                                @JsonProperty("id") String id,
                                @JsonProperty("models") List<ModelDto> models,
                                @JsonProperty("is_deleted") boolean isDeleted){
        this.isFullEnroll = isFullEnroll;
        this.id = id;
        this.models = models;
        this.isDeleted = isDeleted;
    }
}
