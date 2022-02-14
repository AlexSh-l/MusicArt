package com.alex.musicart.model.service;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Category;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.validator.UserValidator;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAllItems() throws ServiceException;


    Optional<Item> findItemByName(String name) throws ServiceException;

    //boolean updateName(long id, String newName, String password) throws ServiceException;

    boolean createNewItem(Item item) throws ServiceException;

    boolean deleteItem(long itemId) throws ServiceException;
}
