package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Item;
import org.apache.logging.log4j.Level;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ItemDao extends RootDao {

    List<Item> findAllItems() throws DaoException;

    Optional<Item> findItemByName(String name) throws DaoException;

    Optional<Item> findItemById(long id) throws DaoException;

    Optional<Item> findItemByCategory(String category) throws DaoException;

    List<Item> findAllItemsInStock() throws DaoException;

    boolean updateItemStock(long id, boolean isInStock) throws DaoException;

    boolean updateItemName(long id, String name) throws DaoException;

    boolean updateItemDescription(long id, String description) throws DaoException;

    boolean updateItemPrice(long id, BigDecimal price) throws DaoException;

    boolean addNewItem(Item item) throws DaoException;

    boolean deleteItem(long itemId) throws DaoException;
}
