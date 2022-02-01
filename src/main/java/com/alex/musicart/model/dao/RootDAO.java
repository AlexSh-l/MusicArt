package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.CustomEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface RootDAO<T extends CustomEntity> {

    Logger logger = LogManager.getLogger();

    default void close(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection wasn't closed. " + e.getMessage());
            throw new DaoException("Connection wasn't closed. ", e);
        }
    }

    default void close(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Statement wasn't closed. " + e.getMessage());
            throw new DaoException("Statement wasn't closed. ", e);
        }
    }
}
