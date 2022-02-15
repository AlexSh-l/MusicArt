package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.OrderStatusDao;
import com.alex.musicart.model.dao.impl.OrderStatusDaoImpl;
import com.alex.musicart.model.entity.OrderStatus;
import com.alex.musicart.model.service.OrderStatusService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class OrderStatusServiceImpl implements OrderStatusService {
    static Logger logger = LogManager.getLogger();
    private static OrderStatusServiceImpl instance;
    private final OrderStatusDao orderStatusDao = new OrderStatusDaoImpl();

    private OrderStatusServiceImpl() {
    }

    public static OrderStatusServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderStatusServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<OrderStatus> findOrderStatusByName(String name) throws ServiceException {
        try {
            Optional<OrderStatus> optionalOrderStatus = orderStatusDao.findOrderStatusByName(name);
            if (optionalOrderStatus.isPresent()) {
                return optionalOrderStatus;
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find order status with set name.");
            throw new ServiceException("Unable to find order status with set name.", e);
        }
        return Optional.empty();
    }
}
