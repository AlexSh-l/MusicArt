package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Order;

import java.util.List;

public interface OrderDao extends RootDao<Order> {

    List<Order> findAllOrders() throws DaoException;

    boolean createOrder(Order order) throws DaoException;

    boolean updateOrderStatus(long id, short orderStatusId) throws DaoException;

    boolean updateOrderPaymentType(long id, short paymentTypeId) throws DaoException;

    boolean updateOrderAddress(long id, String address) throws DaoException;

    boolean deleteOrder(long orderId) throws DaoException;
}
