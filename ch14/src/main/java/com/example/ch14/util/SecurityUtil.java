package com.example.ch14.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {
    public static String verifier(){
        SecureRandom secureRandom = new SecureRandom();
        byte [] code = new byte[32];
        secureRandom.nextBytes(code);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(code);
    }

    public static String challenge() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        byte [] digested = messageDigest.digest(verifier().getBytes());
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(digested);
    }
}
