package net.codjo.ads;
import net.codjo.test.common.LogString;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
/**
 *
 */
public class AdsServiceMock extends AdsService {
    private static final String ADS_USER_PREFIX = "adsUserId";
    private static final String ALL_USER = "*all-users*";
    private final LogString log;
    private final Map<String, String[]> roles = new HashMap<String, String[]>();
    private Map<String, String[]> projects = new HashMap<String, String[]>();
    private Map<String, AdsException> loginErrors = new HashMap<String, AdsException>();


    public AdsServiceMock() {
        this(new LogString());
    }


    public AdsServiceMock(LogString log) {
        super(null, null);
        this.log = log;
    }


    @Override
    public UserToken login(String login, String password, int securityLevel)
          throws AdsSystemException, AccountLockedException {
        log.call("login", login, password, securityLevel);

        if (loginErrors.containsKey(ALL_USER)) {
            throwException(loginErrors.get(ALL_USER));
        }
        if (loginErrors.containsKey(login)) {
            throwException(loginErrors.get(login));
        }
        return new UserToken(new de.dit.ads.auth.UserToken(String.format("%s.%s", ADS_USER_PREFIX, login),
                                                           password,
                                                           "givenName",
                                                           "John Doe",
                                                           "R24",
                                                           "EmployeNo2",
                                                           Locale.FRANCE,
                                                           new Date()));
    }


    private void throwException(AdsException adsException)
          throws AdsSystemException, AccountLockedException {
        if (adsException instanceof AdsSystemException) {
            throw (AdsSystemException)adsException;
        }
        else {
            throw (AccountLockedException)adsException;
        }
    }


    @Override
    public String[] rolesFor(UserToken userToken) throws AdsSystemException {
        log.call("rolesFor", userToken.getUserId());
        return roles.get(userToken.getUserId());
    }


    @Override
    public String[] projectsFor(UserToken userToken) throws AdsSystemException {
        log.call("projectsFor", userToken.getUserId());
        return projects.get(userToken.getUserId());
    }


    @Override
    public void disconnectAll() throws AdsSystemException {
        log.call("disconnectAll");
    }


    public void mockLoginFailureException(String message) {
        mockLoginBehaviour(ALL_USER, new AdsSystemException(message));
    }


    public void mockAccountLockedException(String message) {
        mockLoginBehaviour(ALL_USER, new AccountLockedException(message));
    }


    public void mockRoles(String login, String... newRoles) {
        roles.put(String.format("%s.%s", ADS_USER_PREFIX, login), newRoles);
    }


    public void mockProjects(String login, String... newProjects) {
        projects.put(String.format("%s.%s", ADS_USER_PREFIX, login), newProjects);
    }


    public void mockLoginBehaviour(String login, AdsException loginError) {
        loginErrors.put(login, loginError);
    }
}
