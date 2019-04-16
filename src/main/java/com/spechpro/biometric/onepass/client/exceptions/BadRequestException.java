package com.spechpro.biometric.onepass.client.exceptions;

public class BadRequestException extends OnePassClientException {
    public BadRequestException(String entity) {
        super(entity);
    }
}
