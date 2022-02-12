package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class OrderMapper implements EntityMapper {
    @Override
    public Optional map(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            Order order = new Order();
            order.setPrice(resultSet.getBigDecimal(ORDERS_PRICE));
            order.setStatusId(resultSet.getShort(ORDERS_STATUS_ID));
            order.setUserId(resultSet.getLong(ORDERS_USER_ID));
            //var t = resultSet.getDate(ORDERS_DATETIME);
            order.setTimestamp(resultSet.getTimestamp(ORDERS_TIMESTAMP));
            order.setPaymentTypeId(resultSet.getShort(ORDERS_PAYMENT_TYPE));
            order.setAddress(resultSet.getString(ORDERS_ADDRESS));
            return Optional.of(order);
        }
        return Optional.empty();
    }
}
