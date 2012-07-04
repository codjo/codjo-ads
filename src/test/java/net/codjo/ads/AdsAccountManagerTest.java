package net.codjo.ads;
import static net.codjo.ads.AdsTest.LDAP_URL;
import static net.codjo.ads.AdsTest.PASSWORD;
import static net.codjo.ads.AdsTest.SERVICE_SECURITY_LEVEL;
import static net.codjo.ads.AdsTest.SSL_URL;

import de.dit.ads.auth.SecurityLevelException;
import de.dit.ads.auth.UserAuthenticationException;
import de.dit.ads.auth.UserToken;
import de.dit.ads.auth.ldap.Authentication;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 */
public class AdsAccountManagerTest {
    private static final String NEW_PASSWORD = "adspwd*8";


    @Test
    @Ignore
    public void test_changePassword() throws Exception {
        AuthenticationService service = new AuthenticationService(LDAP_URL, LDAP_URL);
        //Creation d'une authentication avec pwd qui n'expire jamais
        // maxPasswordAge = the number of days until a password expires. ('0' means no expiration).
        // Is not checked for security level 0
        int maxPasswordAge = 0;
        Authentication authentication = Authentication.createInstance(true,
                                                                      LDAP_URL,
                                                                      SSL_URL,
                                                                      maxPasswordAge);
        String[] logins = new String[]{
              "frsubmit1", "frsubmit2", "frsubmit3",
              "frcontrol1", "frcontrol2", "frcontrol3",
              "frnormal1", "frnormal2", "frnormal3",
              "frsimple1", "frsimple2", "frsimple3",
        };

        for (String login : logins) {
            changePwd(service, authentication, login);
        }
        service.disconnectAll();
    }


    private void changePwd(AuthenticationService service,
                           Authentication authentication,
                           String login)
          throws de.dit.ads.common.AdsSystemException, UserAuthenticationException, AdsSystemException {
        try {
            UserToken userToken = service.authenticate(login, PASSWORD, AdsTest.USER_SECURITY_LEVEL);
            System.out.println("userToken = " + userToken);
            authentication.setAllowChangePwdWithoutRules(true);
            authentication.changePwdWithoutPwdRuleCheck(login, PASSWORD, NEW_PASSWORD);
            System.out.println("pwd change pour le compte " + login);
        }
        catch (AdsSystemException e) {
            try {
                System.out.println("pwd pas changé pour le compte " + login);
                e.printStackTrace();
            }
            catch (Exception ee) {
                System.out
                      .println("ERROR pendant changement pwd pour le compte " + login + " \n"
                               + ee.getLocalizedMessage());
            }
        }
        catch (AccountLockedException e) {
            System.out.println("compte bloqué " + login);
        }
    }

}
