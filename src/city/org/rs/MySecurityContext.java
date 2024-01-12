package city.org.rs;

import java.security.Principal;

public class MySecurityContext implements jakarta.ws.rs.core.SecurityContext {

    private final MyPrincipal principal;

    public MySecurityContext(MyPrincipal principal) {
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        // return principal.getRole().equals(role);
        return true;
    }

    @Override
    public boolean isSecure() {
        // You may implement this based on your security requirements
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer"; // Or another authentication scheme you are using
    }
}
