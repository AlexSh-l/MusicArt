package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.ItemDao;
import com.alex.musicart.model.entity.Item;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class ItemDaoImplTest {

    private static Item expected;

    @BeforeClass
    public static void initializeExpectedItem() {
        expected = new Item();
        expected.setItemId(7);
        expected.setSubcategoryId(1);
        expected.setName("PreSonus Eris E5");
        expected.setPrice(BigDecimal.valueOf(614.87));
        expected.setInStock(true);
        expected.setDescription("Тип усиления: Активное;\n" +
                "Количество полос: 2;\n" +
                "Частотный диапазон: 53 - 22 000 Гц;\n" +
                "Общее звуковое давление: 102 дБ;\n" +
                "Частота кроссовера: 3 кГц;\n" +
                "Акустическое оформление: Фазоинверторного типа;\n" +
                "Расположение фазоинвертора: спереди.");
    }

    @Test
    public void findImageByItemId() {
        ItemDao itemDao = new ItemDaoImpl();
        long itemId = 7;
        try {
            Optional<Item> optionalItem = itemDao.findItemById(itemId);
            if (optionalItem.isPresent()) {
                Item actual = optionalItem.get();
                Assert.assertEquals(expected, actual);
            } else {
                Assert.fail();
            }
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }
}
