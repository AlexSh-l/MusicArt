package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.alex.musicart.controller.command.PagePath.ORDER_ITEMS_PAGE;
import static com.alex.musicart.controller.command.ParameterName.ORDER_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class ToOrderItemsCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final OrderServiceImpl orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        long orderId = Long.parseLong(request.getParameter(ORDER_ID));
        try {
            List<Item> items = orderService.findAllItemsOfOrder(orderId);
            session.setAttribute(ITEMS, items);
            session.setAttribute(CURRENT_PAGE, ORDER_ITEMS_PAGE);
            router.setPagePath(ORDER_ITEMS_PAGE);
            router.setRoute(Router.RouteType.FORWARD);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find any items of this order.");
            throw new CommandException("Could not find any items of this order.", e);
        }

    }
}
