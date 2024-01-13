package city.org.rs.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    // Hash a password
    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    // Verify a password against its hash
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
        return result.verified;
    }

    // Example usage
    public static void main(String[] args) {
        String originalPassword = "user123";

        // Hash the password before storing it in the database
        String hashedPassword = hashPassword(originalPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        // Verify a password during login
        boolean isPasswordCorrect = verifyPassword("user123", hashedPassword);
        System.out.println("Password Correct: " + isPasswordCorrect);
    }
}

