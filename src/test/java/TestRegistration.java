import com.spechpro.biometric.onepass.client.api.OnePassApi;
import com.spechpro.biometric.onepass.client.api.PersonApi;
import com.spechpro.biometric.onepass.client.api.RegistrationApi;
import com.spechpro.biometric.onepass.client.api.SessionApi;
import com.spechpro.biometric.onepass.client.util.TestHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

/**
 * Created by sadurtinova on 03.05.2018.
 */
public class TestRegistration {

    private static final String PERSON_1 = "test1";
    private static final String PERSON_2 = "test2";

    private static final String PROTOCOL = "https";
    private static final String HOST = "onepass.tech";
    private static final String PORT = null;
    private static final String APPLICATION_ROOT = "devvkop/rest";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "QL0AFWMIX8NRZTKeof9cXsvbvu8=";
    private static final int DOMAIN_ID = 201;

    public static final String PASSWORD_1 = "ноль один два три четыре пять шесть семь восемь девять";
    public static final String PASSWORD_2 = "девять восемь семь шесть пять четыре три два один ноль";
    public static final String PASSWORD_3 = "пять три восемь девять семь один четыре шесть два ноль";
    private static UUID sessionId;
    private static OnePassApi onePassApi;
    private static SessionApi sessionApi;
    private static TestHelper helper = new TestHelper();
    private static final File REGISTRATION_PHOTO = new File("registrationPhoto.JPG");

    @BeforeClass
    public static void createApiAndGetSessionAndRegisterPerson2() {
        onePassApi = new OnePassApi(PROTOCOL, HOST, PORT, APPLICATION_ROOT);
        sessionApi = new SessionApi(USERNAME, PASSWORD, DOMAIN_ID);
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_2, sessionId.toString());
        RegistrationApi registrationApi = personApi.startRegistration(sessionId);
        registrationApi.createPerson();
        registrationApi.sendRegistrationPhoto(helper.searchFileInResource("registrationPhoto.JPG"));
        registrationApi.sendDynamicRegistrationVoice(PASSWORD_1, helper.searchFileInResource("0123456789.wav"));
        registrationApi.sendDynamicRegistrationVoice(PASSWORD_2, helper.searchFileInResource("9876543210.wav"));
        registrationApi.sendDynamicRegistrationVoice(PASSWORD_3, helper.searchFileInResource("5389714620.wav"));
    }

    @Test
    public void testRegistration_PersonIsNotRegistered_ReturnsFalse(){
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_1, sessionId.toString());
        boolean registered = personApi.exists();
        Assert.assertFalse(registered);
    }

    @Test(expected = Exception.class)
    public void testPhotoRegistration_PersonIsNotRegistered_ThrowsNotInitializedTransactionException() {
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_1, sessionId.toString());
        RegistrationApi registrationApi = personApi.startRegistration(sessionId);
    }

    @Test(expected = Exception.class)
    public void testRegistration_DuplicatePerson_ThrowsBadRequestException() {
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_2, sessionId.toString());
        RegistrationApi registrationApi = personApi.startRegistration(sessionId);
    }

    @Test(expected = Exception.class)
    public void testRegistration_DeleteNotRegisteredPerson_ThrowsBadRequestException(){
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_1, sessionId.toString());
    }

    @AfterClass
    public static void deleteTestPersons() {
        PersonApi personApi2 = onePassApi.person(PERSON_2, sessionId.toString());
        personApi2.delete();

    }

}
