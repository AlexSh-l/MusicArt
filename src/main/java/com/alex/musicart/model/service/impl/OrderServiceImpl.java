package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.impl.OrderDaoImpl;
import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public List<Order> findAllOrders() throws ServiceException {
        try {
            return orderDao.findAllOrders();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all orders.");
            throw new ServiceException("Unable to find all orders.", e);
        }
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

    @Override
    public boolean deleteOrder(long orderId) throws ServiceException {
        try {
            //if (UserValidator.isNameValid(name) && UserValidator.isLoginValid(login) && UserValidator.isPasswordValid(password) && UserValidator.isEmailValid(email) && UserValidator.isPhoneValid(phone)) {
            return orderDao.deleteOrder(orderId);
            //}
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to create order.");
            throw new ServiceException("Unable to create order.", e);
        }
    }
}
