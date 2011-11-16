package net.codjo.ads;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
/**
 *
 */
public class AuthorizationServiceTest implements AdsTest {
    private AuthorizationService authorizationService;


    @Test
    public void test_notConfigured() throws Exception {
        try {
            authorizationService = new AuthorizationService(null, null, null);
            fail();
        }
        catch (Exception e) {
            assertEquals("ldapUrl not set", e.getLocalizedMessage());
        }

        try {
            new AuthorizationService("ldapUrl", null, null);
            fail();
        }
        catch (Exception e) {
            assertEquals("sslUrl not set", e.getLocalizedMessage());
        }

        try {
            new AuthorizationService("ldapUrl", "sslUrl", null);
            fail();
        }
        catch (Exception e) {
            assertEquals("projectEnvironment not set", e.getLocalizedMessage());
        }
    }


    @Test
    public void test_getRolesForUser() throws Exception {
        initAuthorizationService();
        String[] strings = authorizationService.getRolesForUser(LOGIN);

        assertArrayEquals(new String[]{"Administrator", "UploadUser"}, strings);
    }


    @Test
    public void test_getRolesForUser_userDontExist() throws Exception {
        initAuthorizationService();
        String[] roles = authorizationService.getRolesForUser("badUser");

        assertNotNull(roles);
        assertArrayEquals(new String[]{}, roles);
    }


    @Test
    public void test_getRoles() throws Exception {
        initAuthorizationService();
        String[] strings = authorizationService.getRoles();

        assertArrayEquals(new String[]{"Administrator", "ProjectManager", "PublishUser", "UploadUser"}, strings);
    }


    @Test
    public void test_getProjectsForUser() throws Exception {
        initAuthorizationService();

        assertProjectsForUser("frnormal1", "FrFwk1");

        assertProjectsForUser("frnormal2", "FrFwk1", "FrFwk2");

        assertProjectsForUser("frnormal3", "FrFwk1", "FrFwk2", "FrFwk3");

        assertProjectsForUser("frsimple1");
    }


    private void assertProjectsForUser(String user, String... expectedProjects) throws AdsSystemException {
        assertArrayEquals(expectedProjects, authorizationService.getProjectsForUser(user));
    }


    private void initAuthorizationService() {
        authorizationService = new AuthorizationService(LDAP_URL, SSL_URL, PROJECT_ENVIRONMMENT);
    }
}
