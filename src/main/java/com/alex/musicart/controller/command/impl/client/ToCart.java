package com.alex.musicart.controller.command.impl.client;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Cart;
import com.alex.musicart.model.entity.Item;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.CART_PAGE;
import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;
import static com.alex.musicart.controller.command.ParameterName.ITEM_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.CART;

public class ToCart implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        router.setPagePath(CART_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
