package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.OrderStatusDao;
import com.alex.musicart.model.entity.OrderStatus;
import com.alex.musicart.model.entity.PaymentType;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.OrderStatusMapper;
import com.alex.musicart.model.mapper.impl.PaymentTypeMapper;
import com.alex.musicart.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OrderStatusDaoImpl implements OrderStatusDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<OrderStatus> mapper = new OrderStatusMapper();

    private static final String SQL_SELECT_ORDER_STATUS_BY_NAME =
            "SELECT st_id, st_name " +
                    "FROM statuses " +
                    "WHERE st_name = ?";

    public Optional<OrderStatus> findOrderStatusByName(String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_STATUS_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find status with set name.");
            throw new DaoException("Unable to find status with set name.", e);
        }
    }
}
