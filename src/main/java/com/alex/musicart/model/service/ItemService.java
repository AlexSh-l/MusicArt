package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findSetAmountOfItemsById(long itemId, int itemAmount) throws ServiceException;

    Optional<Long> findLastItemByIdWithSetAmount(int itemAmount) throws ServiceException;

    Optional<Item> findItemByName(String name) throws ServiceException;

    Optional<Item> findItemById(long id) throws ServiceException;

    boolean updateItem(Item item, String name, int subcategoryId, String description, BigDecimal price, boolean isInStock) throws ServiceException;

    boolean createNewItem(Item item) throws ServiceException;

    boolean deleteItem(long itemId) throws ServiceException;

    boolean addNewImage(long itemId, String imageName, String imagePath) throws ServiceException;

    List<Item> findImagesForSetItems(List<Item> items) throws ServiceException;
}
