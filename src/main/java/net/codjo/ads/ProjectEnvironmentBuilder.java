package net.codjo.ads;
/**
 *
 */
public class ProjectEnvironmentBuilder {
    private ProjectEnvironment projectEnvironment = new ProjectEnvironment();


    public ProjectEnvironment get() {
        return projectEnvironment;
    }


    public ProjectEnvironmentBuilder setProjectName(String projectName) {
        projectEnvironment.setProjectName(projectName);
        return this;
    }


    public ProjectEnvironmentBuilder setUserBase(String userBase) {
        projectEnvironment.setUserBase(userBase);
        return this;
    }


    public ProjectEnvironmentBuilder setSecurityPrincipal(String securityPrincipal) {
        projectEnvironment.setSecurityPrincipal(securityPrincipal);
        return this;
    }


    public ProjectEnvironmentBuilder setSecurityCredentials(String securityCredential) {
        projectEnvironment.setSecurityCredentials(securityCredential);
        return this;
    }


    public ProjectEnvironmentBuilder setRoleBase(String roleBase) {
        projectEnvironment.setRoleBase(roleBase);
        return this;
    }


    public ProjectEnvironmentBuilder setProjectBase(String projectBase) {
        projectEnvironment.setProjectBase(projectBase);
        return this;
    }


    public ProjectEnvironmentBuilder setPermissionBase(String permissionBase) {
        projectEnvironment.setPermissionBase(permissionBase);
        return this;
    }
}
