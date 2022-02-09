package com.alex.musicart.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptEncryptor {

    public static String hashPassword(String password, String salt){
        return BCrypt.hashpw(password, salt);
    }

    private BCryptEncryptor() {
    }
}
