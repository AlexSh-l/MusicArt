package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.impl.ItemDaoImpl;
import com.alex.musicart.model.dao.impl.OrderDaoImpl;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    static Logger logger = LogManager.getLogger();
    private static OrderServiceImpl instance;
    private final OrderDaoImpl orderDao = new OrderDaoImpl();
    private final ItemDaoImpl itemDao = new ItemDaoImpl();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    public List<Item> findAllItemsOfOrder(long orderId) throws ServiceException {
        try {
            return itemDao.findAllItemsByOrderId(orderId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all items of this order.");
            throw new ServiceException("Unable to find all items of this order.", e);
        }
    }

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

    public boolean updateOrder(Order order, short orderStatusId, short orderPaymentTypeId, String orderAddress) throws ServiceException {
        try {
            boolean isStatusUpdated = false;
            boolean isPaymentTypeUpdated = false;
            boolean isAddressUpdated = false;
            if ((order.getStatusId() != orderStatusId) && (orderStatusId != 0)) {
                isStatusUpdated = orderDao.updateOrderStatus(order.getOrderId(), orderStatusId);
            }
            if ((order.getPaymentTypeId() != orderPaymentTypeId) && (orderPaymentTypeId != 0)) {
                isPaymentTypeUpdated = orderDao.updateOrderPaymentType(order.getOrderId(), orderPaymentTypeId);
            }
            if ((!order.getAddress().equals(orderAddress)) && !orderAddress.isEmpty()) {
                isAddressUpdated = orderDao.updateOrderAddress(order.getOrderId(), orderAddress);
            }
            return (isStatusUpdated || isPaymentTypeUpdated || isAddressUpdated);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update order.");
            throw new ServiceException("Unable to update order.", e);
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
