package com.alex.musicart.controller.command.impl.general;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.ParameterName.LANGUAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.CURRENT_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.LOCALE;

public class ChangeLocaleCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String language = request.getParameter(LANGUAGE);
        session.setAttribute(LOCALE, language);
        router.setPagePath(currentPage);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
