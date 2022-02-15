package com.alex.musicart;

import com.alex.musicart.validator.UserValidator;
import org.junit.Assert;
import org.junit.Test;

public class UserValidatorTest {

    @Test
    public void isNameValidTest() {
        Assert.assertTrue(UserValidator.isNameValid("Boris"));
    }

    @Test
    public void isLoginValidTest() {
        Assert.assertTrue(UserValidator.isLoginValid("borrryA9"));
    }

    @Test
    public void isPasswordValidTest() {
        Assert.assertTrue(UserValidator.isPasswordValid("Bro8efwhrg"));
    }

    @Test
    public void isEmailValidTest() {
        Assert.assertTrue(UserValidator.isEmailValid("borya@gmail.com"));
    }

    @Test
    public void isPhoneValidTest() {
        Assert.assertTrue(UserValidator.isPhoneValid("+378325678990"));
    }

    @Test
    public void isUserValidTest() {
        Assert.assertTrue(UserValidator.isUserValid("Boris", "borrryA9", "Bro8efwhrg", "borya@gmail.com", "+378325678990"));
    }
}
