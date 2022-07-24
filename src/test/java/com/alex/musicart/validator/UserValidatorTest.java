package com.alex.musicart.validator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserValidatorTest {

    private static String expectedName;
    private static String expectedLogin;
    private static String expectedPassword;
    private static String expectedEmail;
    private static String expectedPhone;

    @BeforeClass
    public static void initializeExpectedValues() {
        expectedName = "Boris";
        expectedLogin = "borrryA9";
        expectedPassword = "Bro8efwhrg";
        expectedEmail = "borya@gmail.com";
        expectedPhone = "+378325678990";
    }

    @Test
    public void isNameValidTest() {
        boolean actual = UserValidator.isNameValid(expectedName);
        Assert.assertTrue(actual);
    }

    @Test
    public void isLoginValidTest() {
        boolean actual = UserValidator.isLoginValid(expectedLogin);
        Assert.assertTrue(actual);
    }

    @Test
    public void isPasswordValidTest() {
        boolean actual = UserValidator.isPasswordValid(expectedPassword);
        Assert.assertTrue(actual);
    }

    @Test
    public void isEmailValidTest() {
        boolean actual = UserValidator.isEmailValid(expectedEmail);
        Assert.assertTrue(actual);
    }

    @Test
    public void isPhoneValidTest() {
        boolean actual = UserValidator.isPhoneValid(expectedPhone);
        Assert.assertTrue(actual);
    }

    @Test
    public void isUserValidTest() {
        boolean actual = UserValidator.isUserValid(expectedName, expectedLogin, expectedPassword, expectedEmail, expectedPhone);
        Assert.assertTrue(actual);
    }
}
