package com.spechpro.biometric.onepass.client.exceptions;

public class OperationForbiddenException extends OnePassClientException {
    public OperationForbiddenException(String entity) {
        super(entity);
    }
}
