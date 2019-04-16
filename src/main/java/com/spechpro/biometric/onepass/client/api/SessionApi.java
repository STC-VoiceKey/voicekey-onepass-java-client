package com.spechpro.biometric.onepass.client.api;

import com.spechpro.biometric.onepass.client.dto.DtoHelper;
import com.spechpro.biometric.onepass.client.dto.GetStartSessionDto;
import com.spechpro.biometric.onepass.client.dto.StartSessionRequestDto;
import com.spechpro.biometric.onepass.client.exceptions.ExceptionMapper;
import com.spechpro.biometric.onepass.client.exceptions.OnePassClientException;
import com.spechpro.biometric.onepass.client.rest.OnePassRestClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by sadurtinova on 01.05.2018.
 */
public class SessionApi {

    private static final Logger LOGGER = Logger.getLogger("SessionApi");
    private String username;
    private String password;
    private int domainId;

    public SessionApi(String username, String passwoord, int domainId) {
        this.username = username;
        this.password = passwoord;
        this.domainId = domainId;
    }

    public UUID startSession() throws OnePassClientException {
        UUID sessionId = null;
        try (CloseableHttpResponse response = OnePassRestClient.get().startSession(new StartSessionRequestDto(username, password, domainId))) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                GetStartSessionDto session =
                        DtoHelper.create(response.getEntity().getContent(),
                                GetStartSessionDto.class);
                sessionId = UUID.fromString(session.sessionId);
            } else {
                ExceptionMapper.map(response);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't start session: " + e.getMessage());
            e.printStackTrace();
        }
        return sessionId;
    }

}
