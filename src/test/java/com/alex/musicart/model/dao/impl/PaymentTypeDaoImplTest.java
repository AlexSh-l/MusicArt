package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.PaymentTypeDao;
import com.alex.musicart.model.entity.PaymentType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class PaymentTypeDaoImplTest {

    private static PaymentTypeDao paymentTypeDao;
    private static PaymentType expectedPaymentType;

    @BeforeClass
    public static void initializeExpectedValues() {
        paymentTypeDao = new PaymentTypeDaoImpl();
        expectedPaymentType = new PaymentType();
        expectedPaymentType.setPaymentTypeId((short) 2);
        expectedPaymentType.setName("наличными");
    }

    @Test
    public void findPaymentTypeByNameTest() {
        String paymentTypeName = "наличными";
        try {
            Optional<PaymentType> optionalPaymentType = paymentTypeDao.findPaymentTypeByName(paymentTypeName);
            if (optionalPaymentType.isPresent()) {
                PaymentType actual = optionalPaymentType.get();
                Assert.assertEquals(expectedPaymentType, actual);
            } else {
                Assert.fail();
            }
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }
}
