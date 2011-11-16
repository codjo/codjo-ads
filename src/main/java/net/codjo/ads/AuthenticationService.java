package net.codjo.ads;
import de.dit.ads.auth.UserToken;
import de.dit.ads.auth.ldap.Authentication;
/**
 *
 */
class AuthenticationService {
    private static final boolean USE_SSL = true;
    private static final String DEFAULT_DOMAIN = "internal";
    private Authentication authentication;


    AuthenticationService(String ldapUrl, String sslUrl) throws AdsSystemException {
        if (ldapUrl == null) {
            throw new AdsRuntimeException("ldapUrl not set");
        }
        if (sslUrl == null) {
            throw new AdsRuntimeException("sslUrl not set");
        }
        authentication = createAuthentication(ldapUrl, sslUrl);
    }


    protected Authentication createAuthentication(String ldapUrl, String sslUrl) throws AdsSystemException {
        try {
            return Authentication.createInstance(USE_SSL, ldapUrl, sslUrl);
        }
        catch (Exception e) {
            throw new AdsSystemException(e);
        }
    }


    public UserToken authenticate(String login, String password, int securityLevel)
          throws AdsSystemException, AccountLockedException {
        try {
            return authentication.authenticate(String.format("%s.%s", DEFAULT_DOMAIN, login),
                                               password,
                                               securityLevel);
        }
        catch (Exception e) {
            if (de.dit.ads.auth.AccountLockedException.class.isInstance(e)) {
                throw new AccountLockedException(e);
            }
            throw new AdsSystemException(e);
        }
    }


    public void disconnectAll() throws AdsSystemException {
        try {
            authentication.disconnectAll();
        }
        catch (Exception e) {
            throw new AdsSystemException(e);
        }
    }
}
