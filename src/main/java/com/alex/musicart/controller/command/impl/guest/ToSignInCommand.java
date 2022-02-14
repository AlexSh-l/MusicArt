package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.PagePath.SIGN_IN_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.CURRENT_PAGE;

public class ToSignInCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, SIGN_IN_PAGE);
        router.setPagePath(SIGN_IN_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
