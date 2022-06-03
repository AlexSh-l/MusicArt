package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.service.OrderService;
import com.alex.musicart.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.ORDER_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class DeleteOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        long orderId = Long.parseLong(request.getParameter(ORDER_ID));
        try {
            if (orderService.deleteOrder(orderId)) {
                session.setAttribute(ORDER_DELETION_RESULT, "Order was successfully deleted.");
            } else {
                session.setAttribute(ORDER_DELETION_RESULT, "Unable to delete this order.");
            }
            session.setAttribute(CURRENT_PAGE, ORDERS_PAGE);
            router.setPagePath(ORDERS_PAGE);
            router.setRoute(Router.RouteType.REDIRECT);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Unable to delete this order.");
            throw new CommandException("Unable to delete this order.", e);
        }
    }
}
