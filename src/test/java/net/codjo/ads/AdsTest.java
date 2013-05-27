package net.codjo.ads;
/**
 *
 */
public interface AdsTest {

    String LDAP_URL = "ldap://AFYTD201.tagi2-d.net:40389";
    String SSL_URL = "ldap://AFYTD201.tagi2-d.net:40636";

    String ADSBV_JNLP_URL = "http://afxia012.itd.intradit.net:81/ADSBV_axis2/adsbvgui.jnlp";

    String PROJECT_NAME = "FrFwk1";
    String APPLICATION_USER_NICK_NAME = "FrFwk1Appl";
    String SECURITY_LEVEL_ZERO_TEST_ACCOUNT = "FrFwk3Appl";
    // Constant : for authorisation
    //    String APPLICATION_USER = "uid=REDAppl,ou=internal,ou=People,dc=dit,dc=de";
    String APPLICATION_USER = String.format("uid=%s,ou=internal,ou=People,dc=dit,dc=de",
                                            APPLICATION_USER_NICK_NAME);
    String APPLICATION_PASSWORD = "adstestpwd*2011";
    String PROJECT_BASE = String.format("dc=%s,ou=Projects,dc=dit,dc=de", PROJECT_NAME);
    String ROLE_BASE = String.format("dc=userroles,dc=%s,ou=Projects,dc=dit,dc=de", PROJECT_NAME);
    String PERMISSION_BASE = String.format("dc=permissions,dc=%s,ou=Projects,dc=dit,dc=de", PROJECT_NAME);
    String USER_BASE = "ou=People,dc=dit,dc=de";

    ProjectEnvironment PROJECT_ENVIRONMMENT = new ProjectEnvironmentBuilder()
          .setProjectName(PROJECT_NAME)
          .setSecurityPrincipal(APPLICATION_USER)
          .setSecurityCredentials(APPLICATION_PASSWORD)
          .setProjectBase(PROJECT_BASE)
          .setUserBase(USER_BASE)
          .setRoleBase(ROLE_BASE)
          .setPermissionBase(PERMISSION_BASE).get();

    String LOGIN = "frnormal1";
    String PASSWORD = "adspwd*12";

    int USER_SECURITY_LEVEL = 1;
    int SERVICE_SECURITY_LEVEL = 0;
}
