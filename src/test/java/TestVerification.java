import com.spechpro.biometric.onepass.client.api.OnePassApi;
import com.spechpro.biometric.onepass.client.api.PersonApi;
import com.spechpro.biometric.onepass.client.api.SessionApi;
import com.spechpro.biometric.onepass.client.api.VerificationApi;
import com.spechpro.biometric.onepass.client.exceptions.BadRequestException;
import com.spechpro.biometric.onepass.client.exceptions.OnePassClientException;
import com.spechpro.biometric.onepass.client.util.TestHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

/**
 * Created by sadurtinova on 03.05.2018.
 */

/**
 * It is not possible to give a positive verification example, as verification password is dynamic
 */
public class TestVerification {

    private static final String PERSON_1 = "test1";
    private static final String PERSON_2 = "test2";
    private static final String PERSON_3 = "test3";
    private static final String PROTOCOL = "https";
    private static final String HOST = "onepass.tech";
    private static final String PORT = null;
    private static final String APPLICATION_ROOT = "vkonepass/rest";
    private static final String USERNAME = "vk_user";
    private static final String PASSWORD = "123";
    private static UUID sessionId;
    private static OnePassApi onePassApi;
    private static SessionApi sessionApi;
    private static final int DOMAIN_ID = 201;
    private static TestHelper helper = new TestHelper();
    private static final File REGISTRATION_PHOTO = new File("registrationPhoto.JPG");

    @BeforeClass
    public static void createApiAndGetSession() throws OnePassClientException {
        onePassApi = new OnePassApi(PROTOCOL, HOST, PORT, APPLICATION_ROOT);
        sessionApi = new SessionApi(USERNAME, PASSWORD, DOMAIN_ID);
        sessionId = sessionApi.startSession();
    }

    @Test(expected = BadRequestException.class)
    public void testVerification_PersonIsNotRegistered_ThrowsBadRequestException(){
        PersonApi personApi = onePassApi.person(PERSON_1, sessionId.toString());
        VerificationApi verificationApi = personApi.startVerification(sessionId.toString());
    }

    @Test(expected = BadRequestException.class)
    public void testVerification_PersonIsNotFullyRegistered_ReturnsTrue() {
        PersonApi personApi = onePassApi.person(PERSON_2, sessionId.toString());
        VerificationApi verificationApi = personApi.startVerification(sessionId.toString());
    }

    @AfterClass
    public static void deleteTestPersons()throws OnePassClientException {
        PersonApi personApi2 = onePassApi.person(PERSON_2, sessionId.toString());
        personApi2.delete();
    }

}
