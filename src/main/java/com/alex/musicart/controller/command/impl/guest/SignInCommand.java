package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.service.UserService;
import com.alex.musicart.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class SignInCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        try {
            Optional<User> optionalUser = userService.findClientByLoginAndPassword(login, password);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                session.setAttribute(SIGN_IN_RESULT, true);
                router.setPagePath(MAIN_PAGE);
            }else {
                session.setAttribute(SIGN_IN_RESULT, false);
                router.setPagePath(REGISTRATION_PAGE);
            }
        //router.setPagePath(REGISTRATION_PAGE);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not authenticate client.");
            throw new CommandException("Could not authenticate client.", e);
        }
    }
}
