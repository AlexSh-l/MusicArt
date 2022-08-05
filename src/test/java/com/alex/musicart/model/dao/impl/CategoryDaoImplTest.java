package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.CategoryDao;
import com.alex.musicart.model.entity.Category;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class CategoryDaoImplTest {
    private static CategoryDao categoryDao;
    private static Category expectedCategory;

    @BeforeClass
    public static void initializeExpectedValues() {
        categoryDao = new CategoryDaoImpl();
        expectedCategory = new Category();
        expectedCategory.setCategoryId(3);
        expectedCategory.setName("Рекордеры");
    }

    @Test
    public void findCategoryByIdTest() {
        int categoryId = 3;
        try {
            Optional<Category> optionalCategory = categoryDao.findCategoryById(categoryId);
            if (optionalCategory.isPresent()) {
                Category actual = optionalCategory.get();
                Assert.assertEquals(expectedCategory, actual);
            } else {
                Assert.fail();
            }
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void findCategoryByNameTest() {
        String categoryName = "Рекордеры";
        try {
            Optional<Category> optionalCategory = categoryDao.findCategoryByName(categoryName);
            if (optionalCategory.isPresent()) {
                Category actual = optionalCategory.get();
                Assert.assertEquals(expectedCategory, actual);
            } else {
                Assert.fail();
            }
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }

    }
}
