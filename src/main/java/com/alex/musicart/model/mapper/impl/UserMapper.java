package com.alex.musicart.model.mapper.impl;

import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.alex.musicart.model.mapper.DatabaseTableName.*;

public class UserMapper implements EntityMapper {

    public UserMapper() {
    }

    @Override
    public Optional<User> map(ResultSet resultSet) throws SQLException {
        //resultSet.last()
        if(resultSet.next()) {
            User user = new User();
            user.setUserId(resultSet.getLong(USERS_ID));
            user.setName(resultSet.getString(USERS_NAME));
            user.setLogin(resultSet.getString(USERS_LOGIN));
            user.setPassword(resultSet.getString(USERS_PASSWORD));
            user.setEmail(resultSet.getString(USERS_EMAIL));
            user.setPhone(resultSet.getString(USERS_PHONE));
            User.UserRole userRole = User.UserRole.values()[(int) resultSet.getLong(USERS_ROLE_ID) - 1];
            user.setRole(userRole);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
