package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class SubcategoryMapper implements EntityMapper {
    public SubcategoryMapper(){
    }

    @Override
    public Optional map(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            Subcategory subcategory = new Subcategory();
            subcategory.setSubcategoryId(resultSet.getInt(SUBCATEGORIES_ID));
            subcategory.setName(resultSet.getString(SUBCATEGORIES_NAME));
            subcategory.setCategoryId(resultSet.getInt(SUBCATEGORIES_CATEGORY_ID));
            return Optional.of(subcategory);
        }
        return Optional.empty();
    }
}
