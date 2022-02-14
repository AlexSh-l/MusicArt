package com.alex.musicart.controller.command.impl.client;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.PagePath.CART_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.CURRENT_PAGE;

public class ToCart implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, CART_PAGE);
        router.setPagePath(CART_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
