package com.alex.musicart.controller.command.impl.guest;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

import static com.alex.musicart.controller.command.PagePath.REGISTRATION_PAGE;
import static com.alex.musicart.controller.command.PagePath.SIGN_IN_PAGE;

public class ToRegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPagePath(REGISTRATION_PAGE);
        return router;
    }
}
