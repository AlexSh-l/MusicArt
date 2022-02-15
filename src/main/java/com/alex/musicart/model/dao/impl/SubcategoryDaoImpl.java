package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.SubcategoryDao;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.mapper.EntityMapper;
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

public class SubcategoryDaoImpl implements SubcategoryDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<Subcategory> mapper = new SubcategoryMapper();

    private static final String SQL_SELECT_ALL_SUBCATEGORIES =
            "SELECT su_id, su_name, su_category_id " +
                    "FROM subcategories";
    private static final String SQL_SELECT_SUBCATEGORY_BY_NAME =
            "SELECT su_id, su_name, su_category_id " +
                    "FROM subcategories " +
                    "WHERE su_name = (?)";
    private static final String SQL_SELECT_SUBCATEGORY_BY_ID =
            "SELECT su_id, su_name, su_category_id " +
                    "FROM subcategories " +
                    "WHERE su_id = (?)";
    private static final String SQL_SELECT_ALL_SUBCATEGORIES_BY_CATEGORY_ID =
            "SELECT su_id, su_name, su_category_id " +
                    "FROM subcategories " +
                    "WHERE su_category_id = (?)";
    private static final String SQL_UPDATE_SUBCATEGORY_NAME =
            "UPDATE subcategories " +
                    "SET su_name = (?)" +
                    "WHERE su_id = (?)";

    @Override
    public List<Subcategory> findAllSubcategories() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_SUBCATEGORIES)) {
            ResultSet resultSet = statement.executeQuery();
            List<Subcategory> subcategories;
            subcategories = mapper.mapList(resultSet);
            return subcategories;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all subcategories.");
            throw new DaoException("Unable to find all subcategories.", e);
        }
    }

    @Override
    public Optional<Subcategory> findSubcategoryByName(String subcategory) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SUBCATEGORY_BY_NAME)) {
            statement.setString(1, subcategory);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find subcategory with that name.");
            throw new DaoException("Unable to find subcategory with that name.", e);
        }
    }

    @Override
    public Optional<Subcategory> findSubcategoryById(int subcategoryId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SUBCATEGORY_BY_ID)) {
            statement.setInt(1, subcategoryId);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find subcategory with that category id.");
            throw new DaoException("Unable to find subcategory with that category id.", e);
        }
    }

    @Override
    public List<Subcategory> findSubcategoriesOfCategory(int categoryId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_SUBCATEGORIES_BY_CATEGORY_ID)) {
            List<Subcategory> subcategories;
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            subcategories = mapper.mapList(resultSet);
            return subcategories;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find subcategories of this category.");
            throw new DaoException("Unable to find subcategories of this category.", e);
        }
    }

    @Override
    public boolean updateSubcategoryName(long id, String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_SUBCATEGORY_NAME)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update subcategory's name.");
                return false;
            }
            logger.log(Level.INFO, "Subcategory's name updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update subcategory's name. Database error.", e);
            throw new DaoException("Unable to update subcategory's name. Database error.", e);
        }
    }
}
