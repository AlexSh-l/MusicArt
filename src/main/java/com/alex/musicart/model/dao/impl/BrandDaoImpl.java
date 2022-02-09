package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Brand;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.BrandMapper;
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

public class BrandDaoImpl {

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<Brand> mapper = new BrandMapper();

    private static final String SQL_SELECT_BRAND =
            "SELECT br_id " +
                    "FROM brands " +
                    "WHERE br_name = (?)";
    private static final String SQL_SELECT_ALL_BRANDS =
            "SELECT br_name " +
                    "FROM brands ";

    private static final String SQL_UPDATE_BRAND_NAME =
            "UPDATE brands " +
                    "SET br_name = (?)" +
                    "WHERE br_id = (?)";

    public List<Brand> findAllBrands() throws DaoException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BRANDS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Brand> brands;
            brands = mapper.mapList(resultSet);
            return brands;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all brands.");
            throw new DaoException("Unable to find all brands.", e);
        }
    }

    public Optional<Brand> findBrandByName(String name) throws DaoException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BRAND)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find brand with that name.");
            throw new DaoException("Unable to find brand with that name.", e);
        }
    }

    public boolean updateBrandName(long id, String name) throws DaoException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BRAND_NAME)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update brand's name.");
                return false;
            }
            logger.log(Level.INFO, "Brand's name updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update brand's name. Database error.", e);
            throw new DaoException("Unable to update brand's name. Database error.", e);
        }
    }
}
