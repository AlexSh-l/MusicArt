package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllClients() throws ServiceException;

    Optional<User> findClientByLoginAndPassword(String login, String password) throws ServiceException;

    boolean isClientPresent(String login) throws ServiceException;

    boolean updateName(long id, String newName, String password) throws ServiceException;

    boolean updateLogin(long id, String newLogin, String password) throws ServiceException;

    boolean updatePassword(long id, String oldPassword, String newPassword) throws ServiceException;

    boolean updateEmail(long id, String newEmail, String password) throws ServiceException;

    boolean updatePhone(long id, String newPhone, String password) throws ServiceException;

    boolean registerUser(User user) throws ServiceException;
}
