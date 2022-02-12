package com.alex.musicart.controller.command.impl.client;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

import static com.alex.musicart.controller.command.PagePath.CART_PAGE;
import static com.alex.musicart.controller.command.PagePath.ORDER_PAGE;

public class ToOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPagePath(ORDER_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
