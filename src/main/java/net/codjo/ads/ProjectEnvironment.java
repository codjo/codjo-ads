package net.codjo.ads;
/**
 *
 */
public class ProjectEnvironment {
    private final de.dit.ads.common.ProjectEnvironment projectEnvironment;


    public ProjectEnvironment() {
        this(new de.dit.ads.common.ProjectEnvironment());
    }


    ProjectEnvironment(de.dit.ads.common.ProjectEnvironment projectEnvironment) {
        this.projectEnvironment = projectEnvironment;
    }


    public String getProjectName() {
        return projectEnvironment.getProjectName();
    }


    public String getPermissionBase() {
        return projectEnvironment.getPermissionBase();
    }


    public String getProjectBase() {
        return projectEnvironment.getProjectBase();
    }


    public String getRoleBase() {
        return projectEnvironment.getRoleBase();
    }


    public String getSecurityCredentials() {
        return projectEnvironment.getSecurityCredentials();
    }


    public String getSecurityPrincipal() {
        return projectEnvironment.getSecurityPrincipal();
    }


    public String getUserBase() {
        return projectEnvironment.getUserBase();
    }


    public void setProjectName(String projectName) {
        projectEnvironment.setProjectName(projectName);
    }


    public void setUserBase(String userBase) {
        projectEnvironment.setUserBase(userBase);
    }


    public void setSecurityPrincipal(String securityPrincipal) {
        projectEnvironment.setSecurityPrincipal(securityPrincipal);
    }


    public void setSecurityCredentials(String securityCredential) {
        projectEnvironment.setSecurityCredentials(securityCredential);
    }


    public void setRoleBase(String roleBase) {
        projectEnvironment.setRoleBase(roleBase);
    }


    public void setProjectBase(String projectBase) {
        projectEnvironment.setProjectBase(projectBase);
    }


    public void setPermissionBase(String permissionBase) {
        projectEnvironment.setPermissionBase(permissionBase);
    }


    public String identifier() {
        return projectEnvironment.identifier();
    }


    de.dit.ads.common.ProjectEnvironment getProjectEnvironment() {
        return projectEnvironment;
    }
}
