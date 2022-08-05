package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.ItemDao;
import com.alex.musicart.model.entity.Item;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDaoImplTest {

    private static Item expectedItemById;
    private static Item expectedItemByName;
    private static List<Item> expectedItems;
    private static ItemDao itemDao;

    @BeforeClass
    public static void initializeExpectedValues() {
        itemDao = new ItemDaoImpl();
        expectedItemById = new Item();
        expectedItemById.setItemId(7);
        expectedItemById.setSubcategoryId(1);
        expectedItemById.setName("PreSonus Eris E5");
        expectedItemById.setPrice(BigDecimal.valueOf(614.87));
        expectedItemById.setInStock(true);
        expectedItemById.setDescription("Тип усиления: Активное;\n" +
                "Количество полос: 2;\n" +
                "Частотный диапазон: 53 - 22 000 Гц;\n" +
                "Общее звуковое давление: 102 дБ;\n" +
                "Частота кроссовера: 3 кГц;\n" +
                "Акустическое оформление: Фазоинверторного типа;\n" +
                "Расположение фазоинвертора: спереди.");
        expectedItemByName = new Item();
        expectedItemByName.setItemId(1);
        expectedItemByName.setName("JBL");
        expectedItemByName.setCategory("Студийный звук");
        expectedItemByName.setSubcategory("Студийные мониторы");
        expectedItemByName.setSubcategoryId(1);
        expectedItemByName.setDescription("Тип усиления: Активное\n" +
                "Количество полос: 2\n" +
                "Частотный диапазон : 49 - 20 000 Гц\n" +
                "Общее звуковое давление : 108 дБ\n" +
                "Частота кроссовера : 1,7 кГц\n" +
                "Акустическое оформление : Фазоинверторного типа\n" +
                "Расположение фазоинвертора : сзади");
        expectedItemByName.setPrice(BigDecimal.valueOf(624.75));
        expectedItemByName.setInStock(true);
        expectedItems = new ArrayList<>();
        expectedItems.add(expectedItemByName);
    }

    @Test
    public void findItemByIdTest() {
        long itemId = 7;
        try {
            Optional<Item> optionalItem = itemDao.findItemById(itemId);
            if (optionalItem.isPresent()) {
                Item actual = optionalItem.get();
                Assert.assertEquals(expectedItemById, actual);
            } else {
                Assert.fail();
            }
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void findAllItemsByOrderIdTest() {
        long orderId = 3;
        try {
            List<Item> actual = itemDao.findAllItemsByOrderId(orderId);
            Assert.assertEquals(expectedItems, actual);
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void findItemByNameTest() {
        String itemName = "JBL";
        try {
            Optional<Item> optionalItem = itemDao.findItemByName(itemName);
            if (optionalItem.isPresent()) {
                Item actual = optionalItem.get();
                Assert.assertEquals(expectedItemByName, actual);
            }
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }
}
