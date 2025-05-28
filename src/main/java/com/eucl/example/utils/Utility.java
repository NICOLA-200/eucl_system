package com.eucl.example.utils;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

public class Utility {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hash(@NotEmpty(message = "The raw string is required.") String raw) {
        return passwordEncoder.encode(raw);
    }

    public static boolean compare(String hashed, String raw) {
        return passwordEncoder.matches(raw, hashed);
    }

    public static  String formatToken(String token) {
        return token.replaceAll("(.{4})(?=.)", "$1-");
    }

    public static String generateToken() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
