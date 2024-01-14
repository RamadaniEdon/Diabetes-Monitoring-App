package city.org.rs.utils;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

import city.org.rs.AuthenticationFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    private static final String SECRET_KEY = "o5lo4IaL/LHPXpVYzhY8R7TxECjO78YdZkEMwoamG4Y="; // Replace with a secure secret key
    private static final long expirationMillis = 3600000; // 1 hour
    // Create JWT
    public static String createToken(String username, String password) {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

      Date now = new Date();
      Date expirationDate = new Date(now.getTime() + expirationMillis);

      return JWT.create()
              .withClaim("username", username)
              .withClaim("password", password)
              .withExpiresAt(expirationDate)
              .sign(algorithm);
    }

    // Verify and decode JWT
    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

    public static String getDecodedUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("username").asString();
    }

    public static String getDecodedPassword(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("password").asString();
    }

    public static String getAuthenticationToken(String authenticationHeader){
        return authenticationHeader.replaceFirst(AuthenticationFilter.AUTHENTICATION_SCHEME + " ", "");
    }

    // Example usage
    public static void main(String[] args) {
        String username = "john_doe";
        String password = "secure_password";

        // Create JWT
        String token = createToken(username, password);
        System.out.println("Token: " + token);

        // Verify and decode JWT
        DecodedJWT decodedJWT = verifyToken(token);
        System.out.println("Decoded JWT:");
        System.out.println("Username: " + getDecodedUsername(decodedJWT));
        System.out.println("Password: " + getDecodedPassword(decodedJWT));
        Date expirationDate = decodedJWT.getExpiresAt();
        System.out.println("Expiration Date: " + expirationDate);
    }
}
