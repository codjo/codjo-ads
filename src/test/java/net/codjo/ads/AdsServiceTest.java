package net.codjo.ads;
import net.codjo.test.common.LogString;
import de.dit.ads.auth.ldap.Authentication;
import de.dit.ads.auth.ldap.Authorization;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.lang.String.format;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
/**
 *
 */
public class AdsServiceTest {
    private AdsServiceTest.AuthenticationServiceMock authentication;
    private AuthorizationServiceMock authorization;
    private LogString authenticationLog = new LogString();
    private LogString authorizationLog = new LogString();
    private static final String SSL_URL_BIDON = "ssl_Url_Bidon";
    private static final String LDAP_URL_BIDON = "ldap_Url_Bidon";
    private static final ProjectEnvironment PROJECT_BIDON = new ProjectEnvironment();


    @Before
    public void setUp() throws Exception {
        authentication = new AuthenticationServiceMock(LDAP_URL_BIDON, SSL_URL_BIDON);
        authenticationLog.assertAndClear(format("createAuthentication(%s, %s)",
                                                LDAP_URL_BIDON,
                                                SSL_URL_BIDON));

        authorization = new AuthorizationServiceMock(LDAP_URL_BIDON, SSL_URL_BIDON, PROJECT_BIDON);
        authorizationLog.assertAndClear(format("createAuthorization(%s, %s)",
                                               LDAP_URL_BIDON,
                                               SSL_URL_BIDON));
    }


    @After
    public void tearDown() throws Exception {
        authentication.disconnectAll();
        authorization.disconnectAll();
    }


    @Test
    public void test_defaultConstructor() throws Exception {
        AdsService adsService = null;
        try {
            adsService = new AdsService(LDAP_URL_BIDON, SSL_URL_BIDON, PROJECT_BIDON);
        }
        catch (Throwable t) {
            fail("il ne doir pas y avoir d'exception");
        }
        finally {
            adsService.disconnectAll();
        }
    }


    @Test
    public void test_login() throws Exception {
        UserToken user = new AdsService(authentication, authorization).login("myLogin",
                                                                             "myPassword",
                                                                             AdsTest.USER_SECURITY_LEVEL);

        authenticationLog.assertContent("authenticate(myLogin, myPassword, 1)");
        assertEquals("myLogin", user.getUserId());
    }


    @Test
    public void test_badLogin() throws Exception {
        authentication = new BadLoginAuthenticationServiceMock();

        try {
            new AdsService(authentication, authorization).login("myLogin",
                                                                "myPassword",
                                                                AdsTest.USER_SECURITY_LEVEL);
            fail();
        }
        catch (Exception e) {
            assertEquals("mauvais login", e.getLocalizedMessage());
        }
    }


    @Test
    public void test_badPassword() throws Exception {
        authentication = new BadPasswordAuthenticationServiceMock();

        try {
            new AdsService(authentication, authorization).login("myLogin",
                                                                "myPassword",
                                                                AdsTest.USER_SECURITY_LEVEL);
            fail();
        }
        catch (Exception e) {
            assertEquals("mauvais mot de passe", e.getLocalizedMessage());
        }
    }


    @Test
    public void test_accountLocked() throws Exception {
        authentication = new AccountLockedAuthenticationServiceMock();

        try {
            new AdsService(authentication, authorization).login("myLogin",
                                                                "myPassword",
                                                                AdsTest.USER_SECURITY_LEVEL);
            fail();
        }
        catch (Exception e) {
            assertEquals("compte bloqué", e.getLocalizedMessage());
        }
    }


    @Test
    public void test_pwExpired() throws Exception {
        authentication = new PwdExpiredAuthenticationServiceMock();

        try {
            new AdsService(authentication, authorization).login("myLogin",
                                                                "myPassword",
                                                                AdsTest.USER_SECURITY_LEVEL);
            fail();
        }
        catch (Exception e) {
            assertEquals("mot de passe expiré", e.getLocalizedMessage());
        }
    }


    @Test
    public void test_rolesFor() throws Exception {
        authorization.mockRoles("userLogin1", "admin", "pinpin");
        String[] roles1 = new AdsService(authentication, authorization).rolesFor(userToken("userLogin1"));
        authorizationLog.assertAndClear("getRolesForUser(userLogin1)");
        assertArrayEquals(new String[]{"admin", "pinpin"}, roles1);

        String[] roles2 = new AdsService(authentication, authorization).rolesFor(userToken("userLogin2"));
        authorizationLog.assertAndClear("getRolesForUser(userLogin2)");
        assertArrayEquals(new String[]{}, roles2);
    }


    @Test
    public void test_projectsFor() throws Exception {
        authorization.mockProjects("userLogin1", "project1", "project2");
        String[] projects1 = new AdsService(authentication,
                                            authorization).projectsFor(userToken("userLogin1"));
        authorizationLog.assertAndClear("getProjectsForUser(userLogin1)");
        assertArrayEquals(new String[]{"project1", "project2"}, projects1);

        String[] projects2 = new AdsService(authentication,
                                            authorization).projectsFor(userToken("userLogin2"));
        authorizationLog.assertAndClear("getProjectsForUser(userLogin2)");
        assertArrayEquals(new String[]{}, projects2);
    }


    private UserToken userToken(String login) {
        return new UserToken(new de.dit.ads.auth.UserToken(login,
                                                           "surname",
                                                           "givenName",
                                                           "commonName",
                                                           "employeNo",
                                                           "EmployeNo2",
                                                           Locale.FRANCE,
                                                           new Date()));
    }


    private class AuthenticationServiceMock extends AuthenticationService {

        AuthenticationServiceMock(String ldapUrl, String sslUrl) throws AdsException {
            super(ldapUrl, sslUrl);
        }


        @Override
        public de.dit.ads.auth.UserToken authenticate(String login, String password, int securityLevel)
              throws AdsSystemException {
            authenticationLog.call("authenticate", login, password, securityLevel);

            mockExceptions();

            return new de.dit.ads.auth.UserToken(login,
                                                 "surname",
                                                 "givenName",
                                                 "commonName",
                                                 "employeNo",
                                                 "EmployeNo2",
                                                 Locale.FRANCE,
                                                 new Date());
        }


        @Override
        protected Authentication createAuthentication(String ldapUrl, String sslUrl) {
            authenticationLog.call("createAuthentication", ldapUrl, sslUrl);
            return null;
        }


        protected void mockExceptions() throws AdsSystemException {
        }


        @Override
        public void disconnectAll() throws AdsSystemException {
        }
    }

    private class BadLoginAuthenticationServiceMock extends AuthenticationServiceMock {
        BadLoginAuthenticationServiceMock() throws AdsException {
            super(LDAP_URL_BIDON, SSL_URL_BIDON);
        }


        @Override
        protected void mockExceptions() throws AdsSystemException {
            throw new AdsSystemException("mauvais login");
        }
    }

    private class AccountLockedAuthenticationServiceMock extends AuthenticationServiceMock {
        AccountLockedAuthenticationServiceMock() throws AdsException {
            super(LDAP_URL_BIDON, SSL_URL_BIDON);
        }


        @Override
        protected void mockExceptions() throws AdsSystemException {
            throw new AdsSystemException("compte bloqué");
        }
    }

    private class PwdExpiredAuthenticationServiceMock extends AuthenticationServiceMock {
        PwdExpiredAuthenticationServiceMock() throws AdsException {
            super(LDAP_URL_BIDON, SSL_URL_BIDON);
        }


        @Override
        protected void mockExceptions() throws AdsSystemException {
            throw new AdsSystemException("mot de passe expiré");
        }
    }

    private class BadPasswordAuthenticationServiceMock extends AuthenticationServiceMock {
        BadPasswordAuthenticationServiceMock() throws AdsException {
            super(LDAP_URL_BIDON, SSL_URL_BIDON);
        }


        @Override
        protected void mockExceptions() throws AdsSystemException {
            throw new AdsSystemException("mauvais mot de passe");
        }
    }

    private class AuthorizationServiceMock extends AuthorizationService {
        private final Map<String, String[]> roles = new HashMap<String, String[]>();
        private final Map<String, String[]> projects = new HashMap<String, String[]>();


        AuthorizationServiceMock(String ldapUrl, String sslUrl, ProjectEnvironment projectEnvironment) {
            super(ldapUrl, sslUrl, projectEnvironment);
        }


        void mockRoles(String userId, String... someRoles) {
            this.roles.put(userId, someRoles);
        }


        @Override
        public String[] getRolesForUser(String userId) {
            authorizationLog.call("getRolesForUser", userId);
            return roles.get(userId) == null ? new String[]{} : roles.get(userId);
        }


        public void mockProjects(String userId, String... someProjects) {
            this.projects.put(userId, someProjects);
        }


        @Override
        public String[] getProjectsForUser(String userId) throws AdsSystemException {
            authorizationLog.call("getProjectsForUser", userId);
            return projects.get(userId) == null ? new String[]{} : projects.get(userId);
        }


        @Override
        protected Authorization createAuthorization(String ldapUrl, String sslUrl) {
            authorizationLog.call("createAuthorization", ldapUrl, sslUrl);
            return null;
        }


        @Override
        public void disconnectAll() {
        }
    }
}
