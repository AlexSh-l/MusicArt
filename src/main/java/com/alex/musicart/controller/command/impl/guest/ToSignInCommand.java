package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

import static com.alex.musicart.controller.command.PagePath.SIGN_IN_PAGE;

public class ToSignInCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setPagePath(SIGN_IN_PAGE);
        return router;
    }
}
