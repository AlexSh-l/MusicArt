package com.alex.musicart;

import com.alex.musicart.validator.UserValidator;
import org.junit.Assert;
import org.junit.Test;

public class UserValidatorTest {

    @Test
    public void isNameValidTest() {
        Assert.assertTrue(UserValidator.isNameValid("Boris"));
    }
}
