package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.UserDao;
import com.alex.musicart.model.dao.impl.CategoryDaoImpl;
import com.alex.musicart.model.dao.impl.ItemDaoImpl;
import com.alex.musicart.model.dao.impl.SubcategoryDaoImpl;
import com.alex.musicart.model.dao.impl.UserDaoImpl;
import com.alex.musicart.model.entity.Category;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.service.ItemService;
import com.alex.musicart.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {
    static Logger logger = LogManager.getLogger();
    private static ItemServiceImpl instance;
    private final ItemDaoImpl itemDao = new ItemDaoImpl();
    private final CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    private final SubcategoryDaoImpl subCategoryDao = new SubcategoryDaoImpl();

    private ItemServiceImpl() {
    }

    public static ItemServiceImpl getInstance() {
        if (instance == null) {
            instance = new ItemServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Item> findAllItems() throws ServiceException {
        try {
            //List<Item> items = itemDao.findAllItems();

            return itemDao.findAllItems();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all items.");
            throw new ServiceException("Unable to find all items.", e);
        }
    }

    @Override
    public Optional<Item> findItemByName(String name) throws ServiceException {
        try {
            Optional<Item> optionalItem = itemDao.findItemByName(name);
            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                int itemSubcategoryId = item.getSubcategoryId();
                Optional<Subcategory> optionalSubcategory = subCategoryDao.findSubcategoryById(itemSubcategoryId);
                if(optionalSubcategory.isPresent()) {
                    Subcategory subcategory = optionalSubcategory.get();
                    item.setSubcategory(subcategory.getName());
                    Optional<Category> optionalCategory = categoryDao.findCategoryById(optionalSubcategory.get().getCategoryId());
                    if(optionalCategory.isPresent()) {
                        Category category = optionalCategory.get();
                        item.setCategory(category.getName());
                        //return Optional.of(item);
                    }
                }
                return Optional.of(item);
            }
            return optionalItem;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find item with set name.");
            throw new ServiceException("Unable to find item with set name.", e);
        }
    }

    /*@Override
    public boolean updateName(long id, String newName, String password) throws ServiceException {
        try {
            return UserValidator.isNameValid(newName) && userDao.updateName(id, newName, password);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update name.");
            throw new ServiceException("Unable to update name.", e);
        }
    }*/


}