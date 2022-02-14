package com.alex.musicart.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryptor {

    public static String hashPassword(String password){
        return DigestUtils.md5Hex(password);
    }

    private PasswordEncryptor() {
    }
}
