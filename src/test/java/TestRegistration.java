import com.spechpro.biometric.onepass.client.api.*;
import com.spechpro.biometric.onepass.client.exceptions.NotInitializedTransactionException;
import com.spechpro.biometric.onepass.client.exceptions.OnePassClientException;
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
    private static final String PERSON_3 = "test3";
    private static final String PERSON_4 = "test4";
    private static final String PERSON_5 = "test5";



    private static final String PROTOCOL = "https";
    private static final String HOST = "onepass.tech";
    private static final String PORT = null;
    private static final String APPLICATION_ROOT = "vkonepass/rest";

    private static final String USERNAME = "vk_user";
    private static final String PASSWORD = "123";
    private static final int DOMAIN_ID = 201;

    public static final String PASSWORD_1 = "ноль один два три четыре пять шесть семь восемь девять";
    public static final String PASSWORD_2 = "девять восемь семь шесть пять четыре три два один ноль";
    public static final String PASSWORD_3 = "четыре девять семь ноль восемь три шесть два один пять";
    private static UUID sessionId;
    private static OnePassApi onePassApi;
    private static SessionApi sessionApi;
    private static TestHelper helper = new TestHelper();
    private static final File REGISTRATION_PHOTO = new File("registrationPhoto.JPG");

    @BeforeClass
    public static void createApiAndGetSessionAndRegisterPerson2() throws OnePassClientException {
        onePassApi = new OnePassApi(PROTOCOL, HOST, PORT, APPLICATION_ROOT);
        sessionApi = new SessionApi(USERNAME, PASSWORD, DOMAIN_ID);
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_2, sessionId.toString());
        RegistrationApi registrationApi = personApi.startRegistration(sessionId);
        registrationApi.createPerson();
        registrationApi.sendRegistrationPhoto(helper.searchFileInResource("registrationPhoto.JPG"));
        registrationApi.sendDynamicRegistrationVoice(PASSWORD_1, helper.searchFileInResource("reg1.wav"));
        registrationApi.sendDynamicRegistrationVoice(PASSWORD_2, helper.searchFileInResource("reg2.wav"));
        registrationApi.sendDynamicRegistrationVoice(PASSWORD_3, helper.searchFileInResource("reg3.wav"));
    }

    @Test
    public void testRegistration_PersonIsNotRegistered_ReturnsFalse() {
        boolean exists;
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_1, sessionId.toString());
        exists = personApi.exists();
        Assert.assertFalse(exists);
    }

    @Test(expected = NotInitializedTransactionException.class)
    public void testPhotoRegistration_PersonIsNotRegistered_ThrowsNotInitializedTransactionException() {
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_3, sessionId.toString());
        RegistrationApi registrationApi = personApi.startRegistration(sessionId);
        registrationApi.sendRegistrationPhoto(helper.searchFileInResource("verificationPhoto.jpg"));
    }

    @Test(expected = OnePassClientException.class)
    public void testRegistration_DuplicatePerson_ThrowsBadRequestException(){
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_2, sessionId.toString());
        RegistrationApi registrationApi = personApi.startRegistration(sessionId);
        registrationApi.createPerson();
    }

    @Test
    public void testRegistration_PersonIsNotFullyRegistered_ReturnsTrue() {
        PersonApi personApi = onePassApi.person(PERSON_4, sessionId.toString());
        RegistrationApi registrationApi = personApi.startRegistration(sessionId);
        boolean registered = (personApi.getDynamicModelsNumber() == 3 && personApi.getFaceModelsNumber() == 1);
        Assert.assertFalse(registered);
    }

    @Test(expected = OnePassClientException.class)
    public void testRegistration_DeleteNotRegisteredPerson_ThrowsBadRequestException() {
        sessionId = sessionApi.startSession();
        PersonApi personApi = onePassApi.person(PERSON_5, sessionId.toString());
        personApi.delete();
    }

    @AfterClass
    public static void deleteTestPersons() throws OnePassClientException {
        PersonApi personApi2 = onePassApi.person(PERSON_2, sessionId.toString());
        personApi2.delete();
        PersonApi personApi1 = onePassApi.person(PERSON_1, sessionId.toString());
        personApi1.delete();

    }

}
