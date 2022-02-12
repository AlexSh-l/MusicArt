package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.PaymentTypeDao;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.PaymentType;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.PaymentTypeMapper;
import com.alex.musicart.model.mapper.impl.SubcategoryMapper;
import com.alex.musicart.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaymentTypeDaoImpl implements PaymentTypeDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<PaymentType> mapper = new PaymentTypeMapper();

    private static final String SQL_SELECT_PAYMENT_TYPE_BY_NAME =
            "SELECT pt_id, pt_name " +
                    "FROM payment_types " +
                    "WHERE pt_name = ?";

    public Optional<PaymentType> findPaymentTypeByName(String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PAYMENT_TYPE_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all items.");
            throw new DaoException("Unable to find all items.", e);
        }
    }
}
