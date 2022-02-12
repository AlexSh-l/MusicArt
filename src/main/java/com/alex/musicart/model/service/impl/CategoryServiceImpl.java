package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.impl.CategoryDaoImpl;
import com.alex.musicart.model.dao.impl.ItemDaoImpl;
import com.alex.musicart.model.dao.impl.SubcategoryDaoImpl;
import com.alex.musicart.model.entity.Category;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CategoryServiceImpl {
    static Logger logger = LogManager.getLogger();
    private static CategoryServiceImpl instance;
    private final ItemDaoImpl itemDao = new ItemDaoImpl();
    private final CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    private final SubcategoryDaoImpl subCategoryDao = new SubcategoryDaoImpl();

    private CategoryServiceImpl() {
    }

    public static CategoryServiceImpl getInstance() {
        if (instance == null) {
            instance = new CategoryServiceImpl();
        }
        return instance;
    }

    public Optional<Category> findCategoryByName(String name) throws ServiceException {
        try {
            Optional<Category> optionalCategory = categoryDao.findCategoryByName(name);
            if (optionalCategory.isPresent()){
                return optionalCategory;
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find category with set name.");
            throw new ServiceException("Unable to find category with set name.", e);
        }
        return Optional.empty();
    }
}
