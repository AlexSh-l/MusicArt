package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.CategoryDao;
import com.alex.musicart.model.dao.ItemDao;
import com.alex.musicart.model.dao.SubcategoryDao;
import com.alex.musicart.model.dao.impl.CategoryDaoImpl;
import com.alex.musicart.model.dao.impl.ItemDaoImpl;
import com.alex.musicart.model.dao.impl.SubcategoryDaoImpl;
import com.alex.musicart.model.entity.Category;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.service.ItemService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {
    static Logger logger = LogManager.getLogger();
    private static ItemServiceImpl instance;
    private final ItemDao itemDao = new ItemDaoImpl();
    private final CategoryDao categoryDao = new CategoryDaoImpl();
    private final SubcategoryDao subCategoryDao = new SubcategoryDaoImpl();

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
                if (optionalSubcategory.isPresent()) {
                    Subcategory subcategory = optionalSubcategory.get();
                    item.setSubcategory(subcategory.getName());
                    Optional<Category> optionalCategory = categoryDao.findCategoryById(optionalSubcategory.get().getCategoryId());
                    if (optionalCategory.isPresent()) {
                        Category category = optionalCategory.get();
                        item.setCategory(category.getName());
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

    @Override
    public Optional<Item> findItemById(long id) throws ServiceException {
        try {
            Optional<Item> optionalItem = itemDao.findItemById(id);
            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                int itemSubcategoryId = item.getSubcategoryId();
                Optional<Subcategory> optionalSubcategory = subCategoryDao.findSubcategoryById(itemSubcategoryId);
                if (optionalSubcategory.isPresent()) {
                    Subcategory subcategory = optionalSubcategory.get();
                    item.setSubcategory(subcategory.getName());
                    Optional<Category> optionalCategory = categoryDao.findCategoryById(subcategory.getCategoryId());
                    if (optionalCategory.isPresent()) {
                        Category category = optionalCategory.get();
                        item.setCategory(category.getName());
                    }
                }
                return Optional.of(item);
            }
            return optionalItem;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find item with set id.");
            throw new ServiceException("Unable to find item with set id.", e);
        }
    }

    @Override
    public boolean updateItem(Item item, String name, int subcategoryId, String description, BigDecimal price, boolean isInStock) throws ServiceException {
        try {
            boolean isNameUpdated = false;
            boolean isSubcategoryUpdated = false;
            boolean isDescriptionUpdated = false;
            boolean isPriceUpdated = false;
            boolean isItemStockUpdated = false;
            if (!item.getName().equals(name)) {
                isNameUpdated = itemDao.updateItemName(item.getItemId(), name);
            }
            if (item.getSubcategoryId() != subcategoryId) {
                isSubcategoryUpdated = itemDao.updateItemSubcategory(item.getItemId(), subcategoryId);
            }
            if (!item.getDescription().equals(description)) {
                isDescriptionUpdated = itemDao.updateItemDescription(item.getItemId(), description);
            }
            if (!item.getPrice().equals(price)) {
                isPriceUpdated = itemDao.updateItemPrice(item.getItemId(), price);
            }
            if (item.isInStock() != isInStock) {
                isItemStockUpdated = itemDao.updateItemStock(item.getItemId(), isInStock);
            }
            return (isNameUpdated || isSubcategoryUpdated || isDescriptionUpdated || isPriceUpdated || isItemStockUpdated);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update item.");
            throw new ServiceException("Unable to update item.", e);
        }
    }

    @Override
    public boolean createNewItem(Item item) throws ServiceException {
        try {
            return itemDao.addNewItem(item);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to create an item.");
            throw new ServiceException("Unable to create an item.", e);
        }
    }

    @Override
    public boolean deleteItem(long itemId) throws ServiceException {
        try {
            return itemDao.deleteItem(itemId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to delete an item.");
            throw new ServiceException("Unable to delete an item.", e);
        }
    }
}
