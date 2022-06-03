package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.ORDER_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class ToOrderEditCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        long orderId = Long.parseLong(request.getParameter(ORDER_ID));
        session.setAttribute(ORDER_ID, orderId);
        session.setAttribute(CURRENT_PAGE, ORDER_EDIT_PAGE);
        router.setPagePath(ORDER_EDIT_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
