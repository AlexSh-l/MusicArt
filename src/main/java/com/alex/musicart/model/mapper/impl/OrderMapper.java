package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class OrderMapper implements EntityMapper {
    @Override
    public Optional map(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Order order = new Order();
            order.setOrderId(resultSet.getLong(ORDERS_ID));
            order.setPrice(resultSet.getBigDecimal(ORDERS_PRICE));
            order.setStatusId(resultSet.getShort(ORDERS_STATUS_ID));
            order.setUserId(resultSet.getLong(ORDERS_USER_ID));
            order.setTimestamp(resultSet.getTimestamp(ORDERS_TIMESTAMP));
            order.setPaymentTypeId(resultSet.getShort(ORDERS_PAYMENT_TYPE));
            order.setAddress(resultSet.getString(ORDERS_ADDRESS));
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> mapList(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setOrderId(resultSet.getLong(ORDERS_ID));
            order.setPrice(resultSet.getBigDecimal(ORDERS_PRICE));
            order.setStatusId(resultSet.getShort(ORDERS_STATUS_ID));
            order.setStatus(resultSet.getString(STATUSES_NAME));
            order.setUserId(resultSet.getLong(ORDERS_USER_ID));
            order.setAddress(resultSet.getString(ORDERS_ADDRESS));
            order.setTimestamp(resultSet.getTimestamp(ORDERS_TIMESTAMP));
            order.setPaymentTypeId(resultSet.getShort(ORDERS_PAYMENT_TYPE));
            order.setPaymentType(resultSet.getString(PAYMENT_TYPES_NAME));
            User user = new User();
            user.setUserId(resultSet.getLong(USERS_ID));
            user.setLogin(resultSet.getString(USERS_LOGIN));
            user.setName(resultSet.getString(USERS_NAME));
            user.setEmail(resultSet.getString(USERS_EMAIL));
            user.setPhone(resultSet.getString(USERS_PHONE));
            order.setUser(user);
            orders.add(order);
        }
        return orders;
    }
}
