package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Order;

import java.util.List;

public interface OrderService {

    List<Item> findAllItemsOfOrder(long orderId) throws ServiceException;

    List<Order> findAllOrders() throws ServiceException;

    boolean createOrder(Order order) throws ServiceException;

    boolean updateOrder(Order order, short orderStatusId, short orderPaymentTypeId, String orderAddress) throws ServiceException;

    boolean deleteOrder(long orderId) throws ServiceException;
}
