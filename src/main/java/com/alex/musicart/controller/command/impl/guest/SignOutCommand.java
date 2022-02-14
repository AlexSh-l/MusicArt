package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.removeAttribute(USER);
        session.removeAttribute(CART);
        session.setAttribute(SIGN_IN_RESULT, false);
        session.setAttribute(CURRENT_PAGE, MAIN_PAGE);
        router.setPagePath(MAIN_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
