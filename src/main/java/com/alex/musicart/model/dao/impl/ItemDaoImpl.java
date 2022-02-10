package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.ItemDao;
import com.alex.musicart.model.entity.Brand;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.ItemMapper;
import com.alex.musicart.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ItemDaoImpl implements ItemDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<Item> mapper = new ItemMapper();

    private static final String SQL_SELECT_ALL_ITEMS =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, br_name " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "JOIN brands ON brands.br_id = (SELECT m2m_brand_id FROM items_m2m_brands WHERE it_id = m2m_item_id)";
    private static final String SQL_SELECT_ITEM_BY_NAME =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, br_name " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "JOIN brands ON brands.br_id = (SELECT m2m_brand_id FROM items_m2m_brands WHERE it_id = m2m_item_id) " +
                    "WHERE it_name = (?)";
    private static final String SQL_SELECT_ITEM_BY_ID =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, br_name " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "JOIN brands ON brands.br_id = (SELECT m2m_brand_id FROM items_m2m_brands WHERE it_id = m2m_item_id) " +
                    "WHERE it_id = (?)";
    private static final String SQL_SELECT_ITEM_BY_CATEGORY =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, br_name " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "JOIN brands ON brands.br_id = (SELECT m2m_brand_id FROM items_m2m_brands WHERE it_id = m2m_item_id) " +
                    "WHERE ca_name = (?)";

    private static final String SQL_SELECT_ALL_ITEMS_IN_STOCK =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, br_name " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "JOIN brands ON brands.br_id = (SELECT m2m_brand_id FROM items_m2m_brands WHERE it_id = m2m_item_id)" +
                    "WHERE it_is_in_stock = b'1'";


    private static final String SQL_UPDATE_ITEM_NAME =
            "UPDATE items " +
                    "SET it_name = (?)" +
                    "WHERE it_id = (?)";
    private static final String SQL_UPDATE_ITEM_DESCRIPTION =
            "UPDATE items " +
                    "SET it_description = (?)" +
                    "WHERE it_id = (?)";
    private static final String SQL_UPDATE_ITEM_PRICE =
            "UPDATE items " +
                    "SET it_price = (?)" +
                    "WHERE it_id = (?)";
    private static final String SQL_UPDATE_ITEM_STOCK =
            "UPDATE items " +
                    "SET it_is_in_stock = b'?'" +
                    "WHERE it_id = (?)";


    public List<Item> findAllItems() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ITEMS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Item> items;
            items = mapper.mapList(resultSet);
            return items;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all items.");
            throw new DaoException("Unable to find all items.", e);
        }
    }

    public Optional<Item> findItemByName(String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find item with that name.");
            throw new DaoException("Unable to find item with that name.", e);
        }
    }

    public Optional<Item> findItemById(long id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find item with that id.");
            throw new DaoException("Unable to find item with that id.", e);
        }
    }

    public Optional<Item> findItemByCategory(String category) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ITEM_BY_CATEGORY)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find item with that category.");
            throw new DaoException("Unable to find item with that category.", e);
        }
    }

    public List<Item> findAllItemsInStock() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ITEMS_IN_STOCK)) {
            ResultSet resultSet = statement.executeQuery();
            List<Item> items;
            items = mapper.mapList(resultSet);
            return items;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all items in stock.");
            throw new DaoException("Unable to find all items in stock.", e);
        }
    }

    public boolean updateItemStock(long id, boolean isInStock) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM_STOCK)) {
            if (isInStock) {
                statement.setString(1, "1");
            } else {
                statement.setString(1, "0");
            }
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update item stock.");
                return false;
            }
            logger.log(Level.INFO, "Item stock updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update item stock. Database error.", e);
            throw new DaoException("Unable to update item stock. Database error.", e);
        }
    }

    public boolean updateItemName(long id, String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM_NAME)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update item's name.");
                return false;
            }
            logger.log(Level.INFO, "Item's name updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update item's name. Database error.", e);
            throw new DaoException("Unable to update item's name. Database error.", e);
        }
    }

    public boolean updateItemDescription(long id, String description) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM_DESCRIPTION)) {
            statement.setString(1, description);
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update item's description.");
                return false;
            }
            logger.log(Level.INFO, "Item's description updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update item's description. Database error.", e);
            throw new DaoException("Unable to update item's description. Database error.", e);
        }
    }

    public boolean updateItemPrice(long id, BigDecimal price) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM_PRICE)) {
            statement.setBigDecimal(1, price);
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update item's price.");
                return false;
            }
            logger.log(Level.INFO, "Item's price updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update item's price. Database error.", e);
            throw new DaoException("Unable to update item's price. Database error.", e);
        }
    }
}
