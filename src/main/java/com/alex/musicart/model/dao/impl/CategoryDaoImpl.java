package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Category;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.CategoryMapper;
import com.alex.musicart.model.mapper.impl.SubcategoryMapper;
import com.alex.musicart.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<Category> mapper = new CategoryMapper();
    //private final EntityMapper<Category> categoryMapper = new CategoryMapper();

    private static final String SQL_SELECT_ALL_CATEGORIES =
            "SELECT ca_id, ca_name " +
                    "FROM categories";
    private static final String SQL_SELECT_CATEGORY_BY_NAME =
            "SELECT ca_id, ca_name " +
                    "FROM categories " +
                    "WHERE ca_name = (?)";
    private static final String SQL_SELECT_CATEGORY_BY_ID =
            "SELECT ca_id, ca_name " +
                    "FROM categories " +
                    "WHERE ca_id = (?)";

    private static final String SQL_UPDATE_CATEGORY_NAME =
            "UPDATE categories " +
                    "SET ca_name = (?)" +
                    "WHERE ca_id = (?)";

    public List<Category> findAllCategories() throws DaoException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CATEGORIES)) {
            ResultSet resultSet = statement.executeQuery();
            List<Category> categories;
            categories = mapper.mapList(resultSet);
            return categories;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all categories.");
            throw new DaoException("Unable to find all categories.", e);
        }
    }

    public Optional<Category> findCategoryByName(String category) throws DaoException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CATEGORY_BY_NAME)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find category with that name.");
            throw new DaoException("Unable to find category with that name.", e);
        }
    }

    public Optional<Category> findCategoryById(int categoryId) throws DaoException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CATEGORY_BY_ID)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find category with that id.");
            throw new DaoException("Unable to find category with that id.", e);
        }
    }

    public boolean updateCategoryName(long id, String name) throws DaoException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CATEGORY_NAME)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update category's name.");
                return false;
            }
            logger.log(Level.INFO, "Category's name updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update category's name. Database error.", e);
            throw new DaoException("Unable to update category's name. Database error.", e);
        }
    }
}
