package city.org.rs;


import java.io.IOException;
import java.security.Key;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.io.IOException;
import java.security.Key;

import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jws;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;

public class JwtFilter implements ContainerRequestFilter{

  @Context
  private SecurityContext securityContext;

  @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
            String role = "ADMIN";
            // requestContext.setSecurityContext(new MySecurityContext(new MyPrincipal(role)));
            
    }
    private static DecodedJWT decodeJwt(String jwtToken) {
        // Your secret key for verifying the token
        String secretKey = "11111111";

        // Decode the JWT token
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(jwtToken);
    }
  
}
