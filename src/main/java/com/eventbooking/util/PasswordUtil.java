package com.eventbooking.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash password while registering
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify password while logging in
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}