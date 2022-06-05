package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.ItemDao;
import com.alex.musicart.model.entity.Item;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class ItemDaoImpl implements ItemDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<Item> mapper = new ItemMapper();

    private static final String SQL_SELECT_ALL_ITEMS =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, it_is_deleted " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "WHERE it_is_deleted = b'0'";
    private static final String SQL_SELECT_ALL_ITEMS_BY_ORDER_ID =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, it_is_deleted " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "JOIN items_m2m_orders ON m2m_item_id = it_id " +
                    "WHERE m2m_order_id = ?";
    private static final String SQL_SELECT_ITEM_BY_NAME =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, it_is_deleted " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "WHERE it_name = (?)" +
                    "AND it_is_deleted = b'0'";
    private static final String SQL_SELECT_ITEM_BY_ID =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, it_is_deleted " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "WHERE it_id = (?)" +
                    "AND it_is_deleted = b'0'";
    private static final String SQL_SELECT_ITEM_BY_CATEGORY =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, it_is_deleted " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "WHERE ca_name = (?)" +
                    "AND it_is_deleted = b'0'";

    private static final String SQL_SELECT_ALL_ITEMS_IN_STOCK =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock, it_is_deleted " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "WHERE it_is_in_stock = b'1'" +
                    "AND it_is_deleted = b'0'";

    private static final String SQL_INSERT_NEW_ITEM =
            "INSERT INTO items " +
                    "(it_name, it_subcategory_id, it_description, it_price, it_is_in_stock) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_ITEM_NAME =
            "UPDATE items " +
                    "SET it_name = (?)" +
                    "WHERE it_id = (?)";
    private static final String SQL_UPDATE_ITEM_SUBCATEGORY_ID =
            "UPDATE items " +
                    "SET it_subcategory_id = ?" +
                    "WHERE it_id = ?";
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
                    "SET it_is_in_stock = b?" +
                    "WHERE it_id = (?)";

    private static final String SQL_DELETE_ITEM =
            "UPDATE items " +
                    "SET it_is_deleted = b?" +
                    "WHERE it_id = (?)";

    private static final String SQL_SELECT_SET_AMOUNT_OF_ITEMS_BY_ID =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "WHERE it_id > ? " +
                    "AND it_is_deleted = b'0'" +
                    "ORDER BY it_id ASC LIMIT ?";

    private static final String SQL_SELECT_LAST_ITEM_ID_WITH_SET_AMOUNT =
            "SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock " +
                    "FROM (SELECT it_id, it_name, ca_name, su_name, it_subcategory_id, it_description, it_price, it_is_in_stock " +
                    "FROM items " +
                    "JOIN subcategories ON subcategories.su_id = items.it_subcategory_id " +
                    "JOIN categories ON categories.ca_id = subcategories.su_category_id " +
                    "WHERE it_is_deleted = b'0'" +
                    "ORDER BY it_id ASC LIMIT ?) AS items " +
                    "ORDER BY it_id DESC LIMIT 1";

    private static final String SQL_INSERT_IMAGES_ITEMS =
            "INSERT INTO images_m2m_items " +
                    "(m2m_it_id, m2m_im_id) " +
                    "VALUES (?, ?)";

    private static final String SQL_INSERT_IMAGE =
            "INSERT INTO images " +
                    "(im_name, im_path) " +
                    "VALUES (?, ?)";

    private static final String SQL_SELECT_IMAGE_BY_NAME_AND_PATH =
            "SELECT im_id, im_name, im_path " +
                    "FROM images " +
                    "WHERE im_name = ? " +
                    "AND im_path = ?";

    private static final String SQL_SELECT_IMAGE_BY_ITEM_ID =
            "SELECT im_id, im_name, im_path " +
                    "FROM images " +
                    "JOIN images_m2m_items ON images.im_id = images_m2m_items.m2m_im_id " +
                    "JOIN items ON items.it_id = images_m2m_items.m2m_it_id " +
                    "WHERE it_id = ?";

    private static final String SQL_UPDATE_ITEM_IMAGE =
            "UPDATE images_m2m_items " +
                    "SET m2m_im_id = ? " +
                    "WHERE m2m_it_id = ?";

    @Override
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

    @Override
    public List<Item> findAllItemsByOrderId(long orderId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ITEMS_BY_ORDER_ID)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            List<Item> items;
            items = mapper.mapList(resultSet);
            return items;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all items of this order.");
            throw new DaoException("Unable to find all items of this order.", e);
        }
    }

    @Override
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

    @Override
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

    @Override
    public List<Item> findSetAmountOfItemsById(long id, int itemAmount) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SET_AMOUNT_OF_ITEMS_BY_ID)) {
            statement.setLong(1, id);
            statement.setInt(2, itemAmount);
            ResultSet resultSet = statement.executeQuery();
            List<Item> items;
            items = mapper.mapList(resultSet);
            return items;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find " + itemAmount + " items with that id.");
            throw new DaoException("Unable to find " + itemAmount + " items with that id.", e);
        }
    }

    @Override
    public Optional<Item> findLastItemByIdWithSetAmount(int itemAmount) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_LAST_ITEM_ID_WITH_SET_AMOUNT)) {
            statement.setInt(1, itemAmount);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find last item with set amount of items.");
            throw new DaoException("Unable to find last item with set amount of items.", e);
        }
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public boolean updateItemSubcategory(long id, int subcategoryId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM_SUBCATEGORY_ID)) {
            statement.setInt(1, subcategoryId);
            statement.setLong(2, id);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update item's subcategory.");
                return false;
            }
            logger.log(Level.INFO, "Item's subcategory updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update item's subcategory. Database error.", e);
            throw new DaoException("Unable to update item's subcategory. Database error.", e);
        }
    }

    @Override
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

    @Override
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

    @Override
    public boolean addNewItem(Item item) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_ITEM)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getSubcategoryId());
            statement.setString(3, item.getDescription());
            statement.setBigDecimal(4, item.getPrice());
            statement.setBoolean(5, item.isInStock());
            boolean isCreated = statement.executeUpdate() == 1;
            if (!isCreated) {
                logger.log(Level.INFO, "Unable to create item.");
                return false;
            }
            logger.log(Level.INFO, "Item created.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to create item. Database error.", e);
            throw new DaoException("Unable to create item. Database error.", e);
        }
    }

    @Override
    public boolean deleteItem(long itemId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ITEM)) {
            statement.setString(1, "1");
            statement.setLong(2, itemId);
            boolean isDeleted = statement.executeUpdate() == 1;
            if (!isDeleted) {
                logger.log(Level.INFO, "Unable to delete item.");
                return false;
            }
            logger.log(Level.INFO, "Item deleted.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete item. Database error.", e);
            throw new DaoException("Unable to delete item. Database error.", e);
        }
    }

    @Override
    public boolean addItemImage(long itemId, long imageId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_IMAGES_ITEMS)) {
            statement.setLong(1, itemId);
            statement.setLong(2, imageId);
            boolean isCreated = statement.executeUpdate() == 1;
            if (!isCreated) {
                logger.log(Level.INFO, "Unable to add item's image.");
                return false;
            }
            logger.log(Level.INFO, "Item's image added.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to add item's image. Database error.", e);
            throw new DaoException("Unable to add item's image. Database error.", e);
        }
    }

    @Override
    public boolean addImage(String imageName, String imagePath) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_IMAGE)) {
            statement.setString(1, imageName);
            statement.setString(2, imagePath);
            boolean isCreated = statement.executeUpdate() == 1;
            if (!isCreated) {
                logger.log(Level.INFO, "Unable to add image.");
                return false;
            }
            logger.log(Level.INFO, "Image added.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to add image. Database error.", e);
            throw new DaoException("Unable to add image. Database error.", e);
        }
    }

    @Override
    public boolean updateImage(long imageId, long itemId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ITEM_IMAGE)) {
            statement.setLong(1, imageId);
            statement.setLong(2, itemId);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update image.");
                return false;
            }
            logger.log(Level.INFO, "Image updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update image. Database error.", e);
            throw new DaoException("Unable to update image. Database error.", e);
        }
    }

    @Override
    public Optional<Long> findImageByNameAndPath(String imageName, String imagePath) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE_BY_NAME_AND_PATH)) {
            statement.setString(1, imageName);
            statement.setString(2, imagePath);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long imageId = resultSet.getLong(IMAGES_ID);
                return Optional.of(imageId);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find image with that name and path.");
            throw new DaoException("Unable to find image with that name and path.", e);
        }
    }

    @Override
    public List<Item> findImagesForSetItems(List<Item> items) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE_BY_ITEM_ID)) {
            long itemId;
            List<Item> itemsWithImages = new ArrayList<>();
            for (var item : items) {
                itemId = item.getItemId();
                statement.setLong(1, itemId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    item.setImageName(resultSet.getString(IMAGES_NAME));
                    item.setImagePath(resultSet.getString(IMAGES_PATH));
                } else {
                    item.setImageName("");
                    item.setImagePath("");
                }
                itemsWithImages.add(item);
            }
            return itemsWithImages;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find images for these items.");
            throw new DaoException("Unable to find images for these items.", e);
        }
    }

    @Override
    public Optional<Long> findImageByItemId(long itemId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE_BY_ITEM_ID)) {
            statement.setLong(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong(IMAGES_ID));
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find image for this item.");
            throw new DaoException("Unable to find image for this item.", e);
        }
    }
}
