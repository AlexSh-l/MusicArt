package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Item;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.ADD_ITEM;
import static com.alex.musicart.controller.command.ParameterName.ITEM_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.CURRENT_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.ITEMS;

public class ToAddItemCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_PAGE, ADD_ITEM_PAGE);
        router.setPagePath(ADD_ITEM_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
