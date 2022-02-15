package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Cart;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.service.impl.UserServiceImpl;
import com.alex.musicart.util.PasswordEncryptor;
import com.alex.musicart.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;
import static com.alex.musicart.controller.command.PagePath.REGISTRATION_PAGE;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String name = request.getParameter(NAME);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        String email = request.getParameter(EMAIL);
        String phone = request.getParameter(PHONE);
        boolean isNameValid = UserValidator.isNameValid(name);
        boolean isLoginValid = UserValidator.isLoginValid(login);
        boolean isPasswordValid = UserValidator.isPasswordValid(password);
        boolean isEmailValid = UserValidator.isEmailValid(email);
        boolean isPhoneValid = UserValidator.isPhoneValid(phone);
        boolean isUserValid = isNameValid && isLoginValid && isPasswordValid && isEmailValid && isPhoneValid;
        try {
            boolean isClientPresent = userService.isClientPresent(login);
            if (isClientPresent) {
                router.setPagePath(REGISTRATION_PAGE);
                session.setAttribute(REGISTRATION_RESULT, "This user is already registered.");
                router.setRoute(Router.RouteType.FORWARD);
            } else {
                if ((password.compareTo(confirmPassword) == 0) && isUserValid) {
                    User user = new User();
                    user.setName(name);
                    user.setLogin(login);
                    user.setPassword(PasswordEncryptor.hashPassword(password));
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setRole(User.UserRole.CLIENT);
                    if (userService.registerUser(user)) {
                        session.setAttribute(REGISTRATION_RESULT, true);
                        session.setAttribute(SIGN_IN_RESULT, true);
                        Cart cart = new Cart();
                        session.setAttribute(CART, cart);
                        session.setAttribute(USER, user);
                        router.setRoute(Router.RouteType.REDIRECT);
                        session.setAttribute(CURRENT_PAGE, MAIN_PAGE);
                        router.setPagePath(MAIN_PAGE);
                        return router;
                    } else {
                        session.setAttribute(REGISTRATION_RESULT, "Could not register user.");
                    }
                } else {
                    session.setAttribute(REGISTRATION_RESULT, "Please fill all the inputs correctly.");
                }
            }
            session.setAttribute(CURRENT_PAGE, REGISTRATION_PAGE);
            router.setPagePath(REGISTRATION_PAGE);
            router.setRoute(Router.RouteType.FORWARD);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not authenticate client.");
            throw new CommandException("Could not authenticate client.", e);
        }
    }
}
