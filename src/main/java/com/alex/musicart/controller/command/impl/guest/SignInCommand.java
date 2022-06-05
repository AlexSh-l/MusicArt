package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Cart;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.User;
import com.alex.musicart.model.service.impl.ItemServiceImpl;
import com.alex.musicart.model.service.impl.UserServiceImpl;
import com.alex.musicart.util.PasswordEncryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.CART;

public class SignInCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final UserServiceImpl userService = UserServiceImpl.getInstance();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String encryptedPassword = PasswordEncryptor.hashPassword(password);
        try {
            Optional<User> optionalUser = userService.findClientByLoginAndPassword(login, encryptedPassword);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                session.setAttribute(SIGN_IN_RESULT, true);
                if (user.getRole() == User.UserRole.ADMIN) {
                    List<Item> items = itemService.findSetAmountOfItemsById(0, 10);
                    session.setAttribute(ITEMS, items);
                } else {
                    Cart cart = new Cart();
                    session.setAttribute(CART, cart);
                }
                session.setAttribute(CURRENT_PAGE, MAIN_PAGE);
                router.setPagePath("/controller?command=to_main&page_number=1");
            } else {
                session.setAttribute(SIGN_IN_RESULT, "Could not sign in.");
                session.setAttribute(CURRENT_PAGE, REGISTRATION_PAGE);
                router.setPagePath(REGISTRATION_PAGE);
            }
            router.setRoute(Router.RouteType.REDIRECT);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not authenticate client.");
            throw new CommandException("Could not authenticate client.", e);
        }
    }
}
