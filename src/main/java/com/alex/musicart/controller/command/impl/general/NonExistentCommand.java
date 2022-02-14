package com.alex.musicart.controller.command.impl.general;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.PagePath.CART_PAGE;
import static com.alex.musicart.controller.command.PagePath.ERROR_404_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.CURRENT_PAGE;

public class NonExistentCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, ERROR_404_PAGE);
        router.setPagePath(ERROR_404_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
