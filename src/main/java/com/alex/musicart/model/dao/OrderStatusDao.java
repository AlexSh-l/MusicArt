package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.OrderStatus;

import java.util.Optional;

public interface OrderStatusDao extends RootDao {
    Optional<OrderStatus> findOrderStatusByName(String name) throws DaoException;
}
