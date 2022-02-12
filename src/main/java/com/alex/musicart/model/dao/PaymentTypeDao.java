package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.PaymentType;

import java.util.Optional;

public interface PaymentTypeDao extends RootDao {
    Optional<PaymentType> findPaymentTypeByName(String name) throws DaoException;
}
