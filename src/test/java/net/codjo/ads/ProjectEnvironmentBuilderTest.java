package net.codjo.ads;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 *
 */
public class ProjectEnvironmentBuilderTest {

    @Test
    public void test_get() throws Exception {
        ProjectEnvironment projectEnvironment = new ProjectEnvironmentBuilder()
              .setPermissionBase("permissionBase")
              .setProjectBase("projectBase")
              .setProjectName("projectName")
              .setRoleBase("roleBase")
              .setSecurityCredentials("securityCredential")
              .setSecurityPrincipal("securityPrincipal")
              .setUserBase("userBase")
              .get();

        assertEquals("permissionBase", projectEnvironment.getPermissionBase());
        assertEquals("projectBase", projectEnvironment.getProjectBase());
        assertEquals("projectName", projectEnvironment.getProjectName());
        assertEquals("roleBase", projectEnvironment.getRoleBase());
        assertEquals("securityCredential", projectEnvironment.getSecurityCredentials());
        assertEquals("securityPrincipal", projectEnvironment.getSecurityPrincipal());
        assertEquals("userBase", projectEnvironment.getUserBase());
    }
}
