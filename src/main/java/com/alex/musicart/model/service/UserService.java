package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> findAllClients() throws ServiceException;

    Optional<User> findClientByLoginAndPassword() throws ServiceException;

    Optional<User> isClientPresent() throws ServiceException;

    boolean updateName(long id, String newName, String password) throws ServiceException;

    boolean updateLogin(long id, String newLogin, String password) throws ServiceException;

    boolean updatePassword(long id, String oldPassword, String newPassword) throws ServiceException;

    boolean updateEmail(long id, String newEmail, String password) throws ServiceException;

    boolean updatePhone(long id, String newPhone, String password) throws ServiceException;

    boolean registerUser(Map<String, String> formValues) throws ServiceException;
}
