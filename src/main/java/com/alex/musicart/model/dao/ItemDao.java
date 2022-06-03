package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemDao extends RootDao {

    List<Item> findAllItems() throws DaoException;

    List<Item> findAllItemsByOrderId(long orderId) throws DaoException;

    Optional<Item> findItemByName(String name) throws DaoException;

    Optional<Item> findItemById(long id) throws DaoException;

    List<Item> findSetAmountOfItemsById(long id, int itemAmount) throws DaoException;

    Optional<Item> findLastItemByIdWithSetAmount(int itemAmount) throws DaoException;

    Optional<Item> findItemByCategory(String category) throws DaoException;

    List<Item> findAllItemsInStock() throws DaoException;

    boolean updateItemSubcategory(long id, int subcategoryId) throws DaoException;

    boolean updateItemStock(long id, boolean isInStock) throws DaoException;

    boolean updateItemName(long id, String name) throws DaoException;

    boolean updateItemDescription(long id, String description) throws DaoException;

    boolean updateItemPrice(long id, BigDecimal price) throws DaoException;

    boolean addNewItem(Item item) throws DaoException;

    boolean deleteItem(long itemId) throws DaoException;

    boolean addItemImage(long itemId, long imageId) throws DaoException;

    boolean addImage(String imageName, String imagePath) throws DaoException;

    boolean updateImage(long imageId, long itemId) throws DaoException;

    Optional<Long> findImageByNameAndPath(String imageName, String imagePath) throws DaoException;

    List<Item> findImagesForSetItems(List<Item> items) throws DaoException;

    Optional<Long> findImageByItemId(long itemId) throws DaoException;
}
