package org.example.UserAuthentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    /**
     * Hash the generated password
     * Taken reference from GEEKSFORGEEKS
     * @param password
     * @return
     */
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hashing algorithm not found", e);
        }
    }

}
