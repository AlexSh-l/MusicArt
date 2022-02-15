package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.SubcategoryDao;
import com.alex.musicart.model.dao.impl.SubcategoryDaoImpl;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.service.SubcategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SubcategoryServiceImpl implements SubcategoryService {
    static Logger logger = LogManager.getLogger();
    private static SubcategoryServiceImpl instance;
    private final SubcategoryDao subcategoryDao = new SubcategoryDaoImpl();

    private SubcategoryServiceImpl() {
    }

    public static SubcategoryServiceImpl getInstance() {
        if (instance == null) {
            instance = new SubcategoryServiceImpl();
        }
        return instance;
    }

    public Optional<Subcategory> findSubcategoryByName(String name) throws ServiceException {
        try {
            Optional<Subcategory> optionalSubcategory = subcategoryDao.findSubcategoryByName(name);
            if (optionalSubcategory.isPresent()) {
                return optionalSubcategory;
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find subcategory with set name.");
            throw new ServiceException("Unable to find subcategory with set name.", e);
        }
        return Optional.empty();
    }
}
