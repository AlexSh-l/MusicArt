package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Cart;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.User;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.LOGIN;
import static com.alex.musicart.controller.command.ParameterName.PASSWORD;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute(USER);
        session.removeAttribute(CART);
        //session.setAttribute(USER, null);
        session.setAttribute(SIGN_IN_RESULT, false);
        router.setPagePath(MAIN_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
