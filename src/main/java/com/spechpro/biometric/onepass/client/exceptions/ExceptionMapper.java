package com.spechpro.biometric.onepass.client.exceptions;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sadurtinova on 07.05.2018.
 */
public class ExceptionMapper {
    public static void map(CloseableHttpResponse response) throws
            AccessViolationException,
            OperationForbiddenException,
            BadRequestException,
            NotFoundException,
            IOException {
        InputStream entity;
        entity = response.getEntity().getContent();

        switch (response.getStatusLine().getStatusCode()) {
            case 401:
                throw new AccessViolationException(IOUtils.toString(entity, "UTF-8"));
            case 403:
                throw new OperationForbiddenException(IOUtils.toString(entity, "UTF-8"));
            case 400:
                throw new BadRequestException(IOUtils.toString(entity, "UTF-8"));
            case 404:
                throw new NotFoundException(IOUtils.toString(entity, "UTF-8"));
            case 500:
                throw new BadRequestException(IOUtils.toString(entity, "UTF-8"));

        }
    }
}
