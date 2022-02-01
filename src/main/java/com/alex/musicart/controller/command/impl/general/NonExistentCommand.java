package com.alex.musicart.controller.command.impl.general;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

import static com.alex.musicart.controller.command.PagePath.ERROR_404_PAGE;

public class NonExistentCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        router.setPagePath(ERROR_404_PAGE);
        return router;
    }
}
