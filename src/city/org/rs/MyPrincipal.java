package city.org.rs;

import java.security.Principal;

public class MyPrincipal implements Principal {

    private final String role;

    public MyPrincipal(String role) {
        this.role = role;
    }


    public String getRole() {
        return role;
    }


    @Override
    public String getName() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }
}
