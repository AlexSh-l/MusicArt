package com.alex.musicart.model.mapper;

import com.alex.musicart.model.entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EntityMapper<T extends CustomEntity> {

    Optional<T> map(ResultSet resultSet) throws SQLException;

    default List<T> mapList(ResultSet resultSet) throws SQLException {
        List<T> entityList = new ArrayList<>();
        while (resultSet.next()) {
            Optional<T> optionalEntity = map(resultSet);
            if (optionalEntity.isPresent()) {
                entityList.add(optionalEntity.get());
            }
        }
        return entityList;
    }
}
