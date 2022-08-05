package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Subcategory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class SubcategoryServiceImplTest {

    private static SubcategoryServiceImpl subcategoryServiceInstance;
    private static Subcategory expectedSubcategory;

    @BeforeClass
    public static void initializeExpectedValues() {
        subcategoryServiceInstance = SubcategoryServiceImpl.getInstance();
        expectedSubcategory = new Subcategory();
        expectedSubcategory.setSubcategoryId(1);
        expectedSubcategory.setName("Студийные мониторы");
        expectedSubcategory.setCategoryId(1);
    }

    @Test
    public void findSubcategoryByNameTest() {
        String subcategoryName = "Студийные мониторы";
        try {
            Optional<Subcategory> optionalSubcategory = subcategoryServiceInstance.findSubcategoryByName(subcategoryName);
            if (optionalSubcategory.isPresent()) {
                Subcategory actual = optionalSubcategory.get();
                Assert.assertEquals(expectedSubcategory, actual);
            } else {
                Assert.fail();
            }
        } catch (ServiceException e) {
            Assert.fail(e.getMessage());
        }
    }
}
