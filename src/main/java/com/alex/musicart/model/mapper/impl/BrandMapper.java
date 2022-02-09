package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.Brand;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class BrandMapper implements EntityMapper {

    public BrandMapper(){
    }

    @Override
    public Optional<Brand> map(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            Brand brand = new Brand();
            brand.setBrandId(resultSet.getInt(BRANDS_ID));
            brand.setName(resultSet.getString(BRANDS_NAME));
            return Optional.of(brand);
        }
        return Optional.empty();
    }
}
