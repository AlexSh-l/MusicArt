package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class ItemMapper implements EntityMapper {

    public ItemMapper(){
    }

    @Override
    public Optional<Item> map(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            Item item = new Item();
            item.setItemId(resultSet.getLong(ITEMS_ID));
            item.setName(resultSet.getString(ITEMS_NAME));
            item.setSubcategoryId(resultSet.getInt(ITEMS_SUBCATEGORY_ID));
            item.setDescription(resultSet.getString(ITEMS_DESCRIPTION));
            item.setPrice(resultSet.getBigDecimal(ITEMS_PRICE));
            item.setInStock(resultSet.getBoolean(ITEMS_IS_IN_STOCK));
            return Optional.of(item);
        }
        return Optional.empty();
    }

    @Override
    public List<Item> mapList(ResultSet resultSet) throws SQLException {
        List<Item> items = new ArrayList<>();

        while (resultSet.next()) {
            //if (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getLong(ITEMS_ID));
                item.setName(resultSet.getString(ITEMS_NAME));

                item.setCategory(resultSet.getString(CATEGORIES_NAME));
                item.setSubcategory(resultSet.getString(SUBCATEGORIES_NAME));

                item.setSubcategoryId(resultSet.getInt(ITEMS_SUBCATEGORY_ID));
                item.setDescription(resultSet.getString(ITEMS_DESCRIPTION));
                item.setPrice(resultSet.getBigDecimal(ITEMS_PRICE));
                item.setInStock(resultSet.getBoolean(ITEMS_IS_IN_STOCK));
                items.add(item);
            //}
        }
        return items;
    }
}
