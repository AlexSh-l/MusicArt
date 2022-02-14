package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders() throws ServiceException;

    boolean createOrder(Order order) throws ServiceException;

    boolean deleteOrder(long orderId) throws ServiceException;
}
