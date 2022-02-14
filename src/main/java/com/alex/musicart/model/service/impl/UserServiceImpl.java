package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.dao.UserDao;
import com.alex.musicart.model.dao.impl.UserDaoImpl;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.service.UserService;
import com.alex.musicart.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    static Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance;
    private final UserDao userDao = new UserDaoImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAllClients() throws ServiceException {
        try {
            return userDao.findAllClients();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find all clients.");
            throw new ServiceException("Unable to find all clients.", e);
        }
    }

    @Override
    public Optional<User> findClientByLoginAndPassword(String login, String password) throws ServiceException {
        try {
            return userDao.findClientByLoginAndPassword(login, password);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find user with set login and password.");
            throw new ServiceException("Unable to find user with set login and password.", e);
        }
    }

    @Override
    public boolean isClientPresent(String login) throws ServiceException {
        try {
            return userDao.findClientByLogin(login);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to find user with set login.");
            throw new ServiceException("Unable to find user with set login.", e);
        }
    }

    @Override
    public boolean updateName(long id, String newName, String password) throws ServiceException {
        try {
            return UserValidator.isNameValid(newName) && userDao.updateName(id, newName, password);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update name.");
            throw new ServiceException("Unable to update name.", e);
        }
    }

    @Override
    public boolean updateLogin(long id, String newLogin, String password) throws ServiceException {
        try {
            return UserValidator.isLoginValid(newLogin) && userDao.updateLogin(id, newLogin, password);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update login.");
            throw new ServiceException("Unable to update login.", e);
        }
    }

    @Override
    public boolean updatePassword(long id, String oldPassword, String newPassword) throws ServiceException {
        try {
            return UserValidator.isPasswordValid(newPassword) && userDao.updatePassword(id, oldPassword, newPassword);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update password.");
            throw new ServiceException("Unable to update password.", e);
        }
    }

    @Override
    public boolean updateEmail(long id, String newEmail, String password) throws ServiceException {
        try {
            return UserValidator.isEmailValid(newEmail) && userDao.updateEmail(id, newEmail, password);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update email.");
            throw new ServiceException("Unable to update email.", e);
        }
    }

    @Override
    public boolean updatePhone(long id, String newPhone, String password) throws ServiceException {
        try {
            return UserValidator.isPhoneValid(newPhone) && userDao.updatePhone(id, newPhone, password);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to update phone.");
            throw new ServiceException("Unable to update phone.", e);
        }
    }

    @Override
    public boolean registerUser(User user) throws ServiceException {
        try {
            boolean name = UserValidator.isNameValid(user.getName()) ;
            boolean login = UserValidator.isLoginValid(user.getLogin());
            boolean password = UserValidator.isPasswordValid(user.getPassword());
            boolean email = UserValidator.isEmailValid(user.getEmail());
            boolean phone = UserValidator.isPhoneValid(user.getPhone());
            if ( name && login && password && email && phone) {
                User.UserRole userRole = user.getRole();
                short roleId = userRole.getRoleId();
                return userDao.createUser(user.getName(), user.getLogin(), user.getPassword(), user.getEmail(), user.getPhone(), roleId);
            }
            return false;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Unable to register user.");
            throw new ServiceException("Unable to register user.", e);
        }
    }
}
