package com.alex.musicart.model.dao.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.dao.UserDao;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.mapper.EntityMapper;
import com.alex.musicart.model.mapper.impl.UserMapper;
import com.alex.musicart.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final EntityMapper<User> mapper = new UserMapper();

    private static final String SQL_SELECT_ALL_CLIENTS =
            "SELECT us_id, us_name, us_login, us_password, us_email, us_phone, us_role_id " +
                    "FROM users " +
                    "JOIN roles ON roles.ro_id = users.us_role_id " +
                    "WHERE ro_name = 'client'";
    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT us_id, us_name, us_login, us_password, us_email, us_phone, us_role_id " +
                    "FROM users " +
                    "JOIN roles " +
                    "WHERE us_login = (?) " +
                    "AND us_password = (?) " +
                    "AND roles.ro_id = users.us_role_id";
    private static final String SQL_SELECT_CLIENT_BY_LOGIN =
            "SELECT us_id, us_name, us_login, us_password, us_email, us_phone, us_role_id " +
                    "FROM users " +
                    "JOIN roles ON roles.ro_id = users.us_role_id " +
                    "WHERE ro_name = 'client' " +
                    "AND us_login = (?)";
    private static final String SQL_UPDATE_USER_NAME =
            "UPDATE users " +
                    "SET us_name = (?) " +
                    "WHERE us_id = (?) " +
                    "AND us_password = (?)";
    private static final String SQL_UPDATE_USER_LOGIN =
            "UPDATE users " +
                    "SET us_login = (?) " +
                    "WHERE us_id = (?) " +
                    "AND us_password = (?) ";
    private static final String SQL_UPDATE_USER_PASSWORD =
            "UPDATE users " +
                    "SET us_password = (?) " +
                    "WHERE us_id = (?) " +
                    "AND us_password = (?)";
    private static final String SQL_UPDATE_USER_EMAIL =
            "UPDATE users " +
                    "SET us_email = (?) " +
                    "WHERE us_id = (?) " +
                    "AND us_password = (?)";
    private static final String SQL_UPDATE_USER_PHONE =
            "UPDATE users " +
                    "SET us_phone = (?) " +
                    "WHERE us_id = (?) " +
                    "AND us_password = (?)";
    private static final String SQL_INSERT_USER =
            "INSERT INTO users " +
                    "(us_name, us_login, us_password, us_email, us_phone, us_role_id) " +
                    "VALUES ((?), (?), (?), (?), (?), (?))";

    @Override
    public List<User> findAllClients() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CLIENTS)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> clients;
            clients = mapper.mapList(resultSet);
            return clients;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find all clients.");
            throw new DaoException("Unable to find all clients.", e);
        }
    }

    @Override
    public Optional<User> findClientByLoginAndPassword(String login, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return mapper.map(resultSet);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find client.");
            throw new DaoException("Unable to find client.", e);
        }
    }

    @Override
    public boolean findClientByLogin(String login) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            Optional<User> optionalUser = mapper.map(resultSet);
            return optionalUser.isPresent();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to find client with set login.");
            throw new DaoException("Unable to find client with set login.", e);
        }
    }

    @Override
    public boolean updateName(long id, String newName, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_NAME)) {
            statement.setString(1, newName);
            statement.setLong(2, id);
            statement.setString(3, password);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update name.");
                return false;
            }
            logger.log(Level.INFO, "Name updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update name. Database error.", e);
            throw new DaoException("Unable to update name. Database error.", e);
        }
    }

    @Override
    public boolean updatePassword(long id, String oldPassword, String newPassword) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setLong(2, id);
            statement.setString(3, oldPassword);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update password.");
                return false;
            }
            logger.log(Level.INFO, "Password updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update password. Database error.", e);
            throw new DaoException("Unable to update password. Database error.", e);
        }
    }

    @Override
    public boolean updateLogin(long id, String newLogin, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_LOGIN)) {
            statement.setString(1, newLogin);
            statement.setLong(2, id);
            statement.setString(3, password);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update login.");
                return false;
            }
            logger.log(Level.INFO, "Login updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update login. Database error.", e);
            throw new DaoException("Unable to update login. Database error.", e);
        }
    }

    @Override
    public boolean updateEmail(long id, String newEmail, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_EMAIL)) {
            statement.setString(1, newEmail);
            statement.setLong(2, id);
            statement.setString(3, password);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update email.");
                return false;
            }
            logger.log(Level.INFO, "Email updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update email. Database error.", e);
            throw new DaoException("Unable to update email. Database error.", e);
        }
    }

    @Override
    public boolean updatePhone(long id, String newPhone, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PHONE)) {
            statement.setString(1, newPhone);
            statement.setLong(2, id);
            statement.setString(3, password);
            boolean isUpdated = statement.executeUpdate() == 1;
            if (!isUpdated) {
                logger.log(Level.INFO, "Unable to update phone.");
                return false;
            }
            logger.log(Level.INFO, "Phone updated.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to update phone. Database error.", e);
            throw new DaoException("Unable to update phone. Database error.", e);
        }
    }

    @Override
    public boolean createUser(String name, String login, String password, String email, String phone, short roleId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, name);
            statement.setString(2, login);
            statement.setString(3, password);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setShort(6, roleId);
            boolean isCreated = statement.executeUpdate() == 1;
            if (!isCreated) {
                logger.log(Level.INFO, "Unable to create user.");
                return false;
            }
            logger.log(Level.INFO, "User created.");
            return true;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Unable to create user. Database error.", e);
            throw new DaoException("Unable to create user. Database error.", e);
        }
    }
}
