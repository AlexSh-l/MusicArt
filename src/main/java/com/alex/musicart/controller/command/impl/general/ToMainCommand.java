package com.alex.musicart.controller.command.impl.general;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;

public class ToMainCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPagePath(MAIN_PAGE);
        return router;
    }
}
