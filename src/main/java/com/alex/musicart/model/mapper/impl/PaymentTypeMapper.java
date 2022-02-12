package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.PaymentType;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class PaymentTypeMapper implements EntityMapper {
    @Override
    public Optional<PaymentType> map(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            PaymentType paymentType = new PaymentType();
            paymentType.setPaymentTypeId(resultSet.getShort(PAYMENT_TYPES_ID));
            paymentType.setName(resultSet.getString(PAYMENT_TYPES_NAME));
            return Optional.of(paymentType);
        }
        return Optional.empty();
    }
}
