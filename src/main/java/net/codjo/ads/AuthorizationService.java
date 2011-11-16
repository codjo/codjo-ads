package net.codjo.ads;
import de.dit.ads.auth.ldap.Authorization;
/**
 *
 */
class AuthorizationService {
    private static final boolean USE_SSL = true;
    private Authorization authorization;
    private de.dit.ads.common.ProjectEnvironment projectEnvironment;


    AuthorizationService(String ldapUrl, String sslUrl, ProjectEnvironment projectEnvironment) {
        if (ldapUrl == null) {
            throw new AdsRuntimeException("ldapUrl not set");
        }
        if (sslUrl == null) {
            throw new AdsRuntimeException("sslUrl not set");
        }
        if (projectEnvironment == null) {
            throw new AdsRuntimeException("projectEnvironment not set");
        }
        this.projectEnvironment = projectEnvironment.getProjectEnvironment();
        authorization = createAuthorization(ldapUrl, sslUrl);
    }


    public String[] getRoles() throws AdsSystemException {
        try {
            return authorization.getRoles(projectEnvironment);
        }
        catch (Exception e) {
            throw new AdsSystemException(e);
        }
    }


    public String[] getRolesForUser(String userName) throws AdsSystemException {
        try {
            return authorization.getRolesForUser(projectEnvironment, userName);
        }
        catch (Exception e) {
            throw new AdsSystemException(e);
        }
    }


    public String[] getProjectsForUser(String userName) throws AdsSystemException {
        try {
            return authorization.getProjectsForUser(projectEnvironment,
                                                    String.format("%s", userName));
        }
        catch (Exception e) {
            throw new AdsSystemException(e);
        }
    }


    public void disconnectAll() throws AdsSystemException {
        try {
            authorization.disconnectAll();
        }
        catch (Exception e) {
            throw new AdsSystemException(e);
        }
    }


    protected Authorization createAuthorization(String ldapUrl, String sslUrl) {
        return Authorization.createInstance(USE_SSL, ldapUrl, sslUrl);
    }
}
