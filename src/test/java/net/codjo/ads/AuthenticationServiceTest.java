package net.codjo.ads;
import de.dit.ads.auth.UserToken;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
/**
 *
 */
public class AuthenticationServiceTest implements AdsTest {

    @Test
    public void test_notConfigured() {
        try {
            new AuthenticationService(null, null);
            fail();
        }
        catch (Exception e) {
            assertEquals("ldapUrl not set", e.getLocalizedMessage());
        }

        try {
            new AuthenticationService("ldapUrl", null);
            fail();
        }
        catch (Exception e) {
            assertEquals("sslUrl not set", e.getLocalizedMessage());
        }
    }


    @Test
    public void test_login() throws Exception {
        AuthenticationService service = new AuthenticationService(LDAP_URL, LDAP_URL);
        UserToken userToken = service.authenticate(LOGIN, PASSWORD, USER_SECURITY_LEVEL);

        assertEquals(String.format("internal.%s", LOGIN), userToken.getUserId());
    }


    @Test
    public void test_loginAsService() throws Exception {
        AuthenticationService service = new AuthenticationService(LDAP_URL, LDAP_URL);
        UserToken userToken = service.authenticate(LOGIN, PASSWORD, SERVICE_SECURITY_LEVEL);

        assertEquals(String.format("internal.%s", LOGIN), userToken.getUserId());
    }


    @Test
    public void test_loginWithServiceAccountAndUserSecurityLevel() throws Exception {
        AuthenticationService service = new AuthenticationService(LDAP_URL, LDAP_URL);
        try {
            service.authenticate(SECURITY_LEVEL_ZERO_TEST_ACCOUNT, APPLICATION_PASSWORD, USER_SECURITY_LEVEL);
        }
        catch (AdsSystemException e) {
            assertEquals(
                  "de.dit.ads.auth.SecurityLevelException: Persons security Level is: 0 - required level is: 1",
                  e.getLocalizedMessage());
        }
    }


    @Test
    public void test_loginWithDummySecLevel() throws Exception {
        AuthenticationService service = new AuthenticationService(LDAP_URL, LDAP_URL);
        try {
            service.authenticate(LOGIN, PASSWORD, 2);
            fail();
        }
        catch (AdsSystemException e) {
            assertEquals(
                  "java.lang.IllegalArgumentException: Invalid Security Level. Please use value between '0' or '1'.",
                  e.getLocalizedMessage());
        }
    }


    @Test
    public void test_badLogin() throws Exception {
        AuthenticationService service = new AuthenticationService(LDAP_URL, SSL_URL);
        try {
            service.authenticate("badLogin", "badPassword", USER_SECURITY_LEVEL);
            fail();
        }
        catch (AdsException e) {
            assertEquals("de.dit.ads.auth.InvalidLoginException: Invalid username or password",
                         e.getLocalizedMessage());
        }
    }
}
