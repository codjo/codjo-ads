package net.codjo.ads;
import de.dit.ads.common.AdsLoggingUtilities;
/**
 *
 */
public class AdsService {
    private final AuthenticationService authentificationService;
    private final AuthorizationService authorizationService;


    static {
        AdsLoggingUtilities.setLoggingConfigured(true);
    }


    public AdsService(String ldapUrl, String sslUrl, ProjectEnvironment project) throws AdsSystemException {
        this(new AuthenticationService(ldapUrl, sslUrl),
             new AuthorizationService(ldapUrl, sslUrl, project));
    }


    protected AdsService(AuthenticationService authenticationService,
                         AuthorizationService authorizationService) {
        this.authentificationService = authenticationService;
        this.authorizationService = authorizationService;
    }


    public UserToken login(String login, String password, int securityLevel)
          throws AdsSystemException, AccountLockedException {
        try {
            return new UserToken(authentificationService.authenticate(login, password, securityLevel));
        }
        finally {
            authentificationService.disconnectAll();
        }
    }


    public String[] rolesFor(UserToken userToken) throws AdsSystemException {
        try {
            return authorizationService.getRolesForUser(userToken.getUserId());
        }
        finally {
            authorizationService.disconnectAll();
        }
    }


    public String[] projectsFor(UserToken userToken) throws AdsSystemException {
        try {
            return authorizationService.getProjectsForUser(userToken.getUserId());
        }
        finally {
            authorizationService.disconnectAll();
        }
    }


    public void disconnectAll() throws AdsSystemException {
        authentificationService.disconnectAll();
        authorizationService.disconnectAll();
    }
}
