package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.Category;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class CategoryMapper implements EntityMapper {

    public CategoryMapper(){
    }

    @Override
    public Optional map(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            Category category = new Category();
            category.setCategoryId(resultSet.getInt(CATEGORIES_ID));
            category.setName(resultSet.getString(CATEGORIES_NAME));
            return Optional.of(category);
        }
        return Optional.empty();
    }
}
