package city.org.rs.utils;

import com.auth0.jwt.interfaces.DecodedJWT;

import city.org.rs.AuthenticationFilter;

public class Helpers {
  public static String getAuthenticationUsername(String authenticationHeader) {
    String token = authenticationHeader.replaceFirst(AuthenticationFilter.AUTHENTICATION_SCHEME + " ", "");
    DecodedJWT decodedJWT = JwtUtil.verifyToken(token);
    return JwtUtil.getDecodedUsername(decodedJWT);
  }
}
