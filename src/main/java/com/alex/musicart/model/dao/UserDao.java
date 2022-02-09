package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends RootDao<User> {

    List<User> findAllClients() throws DaoException;

    Optional<User> findClientByLoginAndPassword(String login, String password) throws DaoException;

    boolean findClientByLogin(String login) throws DaoException;

    boolean updateName(long id, String newName, String password) throws DaoException;

    boolean updateLogin(long id, String newLogin, String password) throws DaoException;

    boolean updatePassword(long id, String oldPassword, String newPassword) throws DaoException;

    boolean updateEmail(long id, String newEmail, String password) throws DaoException;

    boolean updatePhone(long id, String newPhone, String password) throws DaoException;

    boolean createUser(String name, String login, String password, String email, String phone, short roleId) throws DaoException;
}
