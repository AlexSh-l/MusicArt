package com.alex.musicart.validator;

public class UserValidator {
    private static final String REGEX_NAME = "[\\w, '-]{1,100}";
    private static final String REGEX_LOGIN = "[\\w]{8,100}";
    private static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,100}$";
    private static final String REGEX_EMAIL = "[\\S]+\\@[\\S]+\\.[\\a-z]+";
    private static final String REGEX_PHONE = "\\+[\\d]{4,12}";

    private UserValidator() {
    }

    public static boolean isNameValid(String name) {
        return name.matches(REGEX_NAME);
    }

    public static boolean isLoginValid(String login) {
        return login.matches(REGEX_LOGIN);
    }

    public static boolean isPasswordValid(String password) {
        return password.matches(REGEX_PASSWORD);
    }

    public static boolean isEmailValid(String email) {
        return email.matches(REGEX_EMAIL);
    }

    public static boolean isPhoneValid(String phone) {
        return phone.matches(REGEX_PHONE);
    }

    public static boolean isUserValid(String name, String login, String password, String email, String phone) {
        return isNameValid(name) && isLoginValid(login) && isPasswordValid(password) && isEmailValid(email) && isPhoneValid(phone);
    }
}
