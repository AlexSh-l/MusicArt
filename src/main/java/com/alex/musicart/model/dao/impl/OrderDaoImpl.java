package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.OrderDao;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.ItemMapper;
import com.alex.musicart.model.mapper.impl.OrderMapper;
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

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<Order> mapper = new OrderMapper();

    private static final String SQL_SELECT_ALL_ORDERS =
            "SELECT   or_id, or_price, or_status_id, st_id, st_name, or_user_id, or_timestamp, or_payment_type, or_address, pt_id, pt_name, us_id, us_name, us_login, us_email, us_phone " +
                    "FROM orders " +
                    "JOIN users " +
                    "JOIN roles " +
                    "JOIN statuses " +
                    "JOIN payment_types " +
                    "WHERE or_user_id = us_id " +
                    "       AND ro_name = 'client' " +
                    "       AND ro_id = us_role_id " +
                    "       AND st_id = or_status_id " +
                    "       AND pt_id = or_payment_type";

    private static final String SQL_SELECT_ORDER_BY_USER_ID =
            "SELECT or_id, or_price, or_status_id, or_user_id, or_timestamp, or_payment_type, or_address, us_id, us_name, us_login, us_email, us_phone " +
                    "FROM orders " +
                    "JOIN users " +
                    "JOIN roles " +
                    "WHERE or_user_id = ? " +
                    "AND or_user_id = us_id " +
                    "AND ro_name = 'client'";

    private static final String SQL_SELECT_LAST_ORDER_BY_USER_ID =
            "SELECT      or_id, or_price, or_status_id, or_user_id, or_timestamp, or_payment_type, or_address, us_id, us_name, us_login, us_email, us_phone " +
                    "FROM orders " +
                    "JOIN users " +
                    "            JOIN roles " +
                    "WHERE or_user_id = ? " +
                    "AND or_user_id = us_id " +
                    "AND ro_name = 'client' " +
                    "ORDER BY or_timestamp DESC;";

    private static final String SQL_SELECT_ORDER_ID_BY_USER_ID =
            "SELECT or_id " +
                    "FROM orders " +
                    "WHERE or_user_id = ? ";

    private static final String SQL_SELECT_ORDER_ITEMS =
            "INSERT INTO items_m2m_orders " +
                    "(m2m_item_id, m2m_order_id) " +
                    "VALUES (?, ?)";

    private static final String SQL_INSERT_NEW_ORDER =
            "INSERT INTO orders " +
                    "(or_price, or_status_id, or_user_id, or_timestamp, or_payment_type, or_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_ORDER_ITEMS =
            "INSERT INTO items_m2m_orders " +
                    "(m2m_item_id, m2m_order_id) " +
                    "VALUES (?, ?)";

    private static final String SQL_DELETE_ORDER_ITEMS_BY_ORDER_ID =
            "DELETE FROM items_m2m_orders " +
                    "WHERE m2m_order_id = ?";

    private static final String SQL_DELETE_ORDER_BY_ORDER_ID =
            "DELETE FROM orders " +
                    "WHERE or_id = ?";

    @Override
    public List<Order> findAllOrders() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Order> orders;
            orders = mapper.mapList(resultSet);
            return orders;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all orders.");
            throw new DaoException("Unable to find all orders.", e);
        }
    }

    @Override
    public boolean createOrder(Order order) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement orderStatement = connection.prepareStatement(SQL_INSERT_NEW_ORDER);
             PreparedStatement createdOrderStatement = connection.prepareStatement(SQL_SELECT_LAST_ORDER_BY_USER_ID);
             PreparedStatement orderItemsStatement = connection.prepareStatement(SQL_INSERT_ORDER_ITEMS)) {
            orderStatement.setBigDecimal(1, order.getPrice());
            orderStatement.setShort(2, order.getStatusId());
            orderStatement.setLong(3, order.getUserId());
            orderStatement.setTimestamp(4, order.getTimestamp());
            orderStatement.setShort(5, order.getPaymentTypeId());
            orderStatement.setString(6, order.getAddress());
            boolean isOrderCreated = orderStatement.executeUpdate() == 1;
            createdOrderStatement.setLong(1, order.getUserId());
            ResultSet resultSet = createdOrderStatement.executeQuery();
            Optional<Order> createdOptionalOrder = mapper.map(resultSet);
            if (createdOptionalOrder.isPresent()) {
                Order createdOrder = createdOptionalOrder.get();
                long orderId = createdOrder.getOrderId();
                List<Item> items = order.getItems();
                for (Item item : items) {
                    orderItemsStatement.setLong(1, item.getItemId());
                    orderItemsStatement.setLong(2, orderId);
                    boolean isOrderItemsCreated = orderItemsStatement.executeUpdate() == 1;
                    if (!isOrderCreated || !isOrderItemsCreated) {
                        logger.log(Level.ERROR, "Unable to create order.");
                        return false;
                    }
                }
                logger.log(Level.INFO, "Order created.");
                return true;
            } else {
                logger.log(Level.ERROR, "Unable to create order.");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to create order.");
            throw new DaoException("Unable to create order.", e);
        }
    }

    @Override
    public boolean deleteOrder(long orderId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement orderItemsStatement = connection.prepareStatement(SQL_DELETE_ORDER_ITEMS_BY_ORDER_ID);
             PreparedStatement orderStatement = connection.prepareStatement(SQL_DELETE_ORDER_BY_ORDER_ID)) {
            orderItemsStatement.setLong(1, orderId);
            boolean areOrderItemIdsDeleted = orderItemsStatement.executeUpdate() > 0;
            orderStatement.setLong(1, orderId);
            boolean isOrderDeleted = orderStatement.executeUpdate() > 0;
            if (!areOrderItemIdsDeleted || !isOrderDeleted) {
                logger.log(Level.ERROR, "Unable to delete order.");
                return false;
            }
            logger.log(Level.INFO, "Order deleted.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to delete order.");
            throw new DaoException("Unable to delete order.", e);
        }
    }
}
