package com.spechpro.biometric.onepass.client.exceptions;

public class NotFoundException extends OnePassClientException {
    public NotFoundException(String entity) {
        super(entity);
    }
}
