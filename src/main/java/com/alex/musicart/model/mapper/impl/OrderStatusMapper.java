package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.OrderStatus;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class OrderStatusMapper implements EntityMapper {
    @Override
    public Optional map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrderStatusId(resultSet.getShort(STATUSES_ID));
            orderStatus.setName(resultSet.getString(STATUSES_NAME));
            return Optional.of(orderStatus);
        }
        return Optional.empty();
    }
}
