package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.PaymentTypeDao;
import com.alex.musicart.model.dao.impl.PaymentTypeDaoImpl;
import com.alex.musicart.model.entity.PaymentType;
import com.alex.musicart.model.service.PaymentTypeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class PaymentTypeServiceImpl implements PaymentTypeService {
    static Logger logger = LogManager.getLogger();
    private static PaymentTypeServiceImpl instance;
    private final PaymentTypeDao paymentTypeDao = new PaymentTypeDaoImpl();

    private PaymentTypeServiceImpl() {
    }

    public static PaymentTypeServiceImpl getInstance() {
        if (instance == null) {
            instance = new PaymentTypeServiceImpl();
        }
        return instance;
    }

    public Optional<PaymentType> findPaymentTypeByName(String name) throws ServiceException {
        try {
            Optional<PaymentType> optionalPaymentType = paymentTypeDao.findPaymentTypeByName(name);
            if (optionalPaymentType.isPresent()) {
                return optionalPaymentType;
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find payment type with set name.");
            throw new ServiceException("Unable to find payment type with set name.", e);
        }
        return Optional.empty();
    }
}
