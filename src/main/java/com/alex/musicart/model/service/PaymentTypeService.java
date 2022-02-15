package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.PaymentType;

import java.util.Optional;

public interface PaymentTypeService {
    Optional<PaymentType> findPaymentTypeByName(String name) throws ServiceException;
}
