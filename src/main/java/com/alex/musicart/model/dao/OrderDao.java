package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Order;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends RootDao<Order> {

    List<Order> findAllOrders() throws DaoException;

    boolean createOrder(Order order) throws DaoException;

    boolean deleteOrder(long orderId) throws DaoException;
}
