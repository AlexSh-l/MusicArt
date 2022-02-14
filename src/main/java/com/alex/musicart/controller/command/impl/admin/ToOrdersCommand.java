package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Order;
import com.alex.musicart.model.service.OrderService;
import com.alex.musicart.model.service.impl.ItemServiceImpl;
import com.alex.musicart.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.alex.musicart.controller.command.PagePath.ADD_ITEM_PAGE;
import static com.alex.musicart.controller.command.PagePath.ORDERS_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class ToOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        try {
            List<Order> orders = orderService.findAllOrders();
            session.setAttribute(ORDERS, orders);
            session.setAttribute(CURRENT_PAGE, ORDERS_PAGE);
            router.setPagePath(ORDERS_PAGE);
            router.setRoute(Router.RouteType.FORWARD);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find any orders.");
            throw new CommandException("Could not find any orders.", e);
        }
    }
}
