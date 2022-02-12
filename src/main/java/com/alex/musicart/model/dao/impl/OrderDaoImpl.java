package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.ItemMapper;
import com.alex.musicart.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<Item> mapper = new ItemMapper();

    private static final String SQL_INSERT_NEW_ORDER =
            "INSERT INTO orders " +
                    "(or_price, or_status_id, or_user_id, or_timestamp, or_payment_type, or_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    public boolean createOrder(Order order) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_ORDER)) {
            statement.setBigDecimal(1, order.getPrice());
            statement.setShort(2, order.getStatusId());
            statement.setLong(3, order.getUserId());
            statement.setTimestamp(4, order.getTimestamp());
            statement.setShort(5, order.getPaymentTypeId());
            statement.setString(6, order.getAddress());
            boolean isCreated = statement.executeUpdate() == 1;
            if (!isCreated) {
                logger.log(Level.INFO, "Unable to create order.");
                return false;
            }
            logger.log(Level.INFO, "Order created.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to create order.");
            throw new DaoException("Unable to create order.", e);
        }
    }
}
