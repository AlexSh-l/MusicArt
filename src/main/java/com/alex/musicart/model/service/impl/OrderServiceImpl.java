package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.impl.OrderDaoImpl;
import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderServiceImpl implements OrderService {
    static Logger logger = LogManager.getLogger();
    private static OrderServiceImpl instance;
    private final OrderDaoImpl orderDao = new OrderDaoImpl();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean createOrder(Order order) throws ServiceException {
        try {
            //if (UserValidator.isNameValid(name) && UserValidator.isLoginValid(login) && UserValidator.isPasswordValid(password) && UserValidator.isEmailValid(email) && UserValidator.isPhoneValid(phone)) {
            return orderDao.createOrder(order);
            //}
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to create order.");
            throw new ServiceException("Unable to create order.", e);
        }
    }
}
