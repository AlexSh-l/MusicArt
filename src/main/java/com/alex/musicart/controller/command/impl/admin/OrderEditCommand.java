package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.*;
import com.alex.musicart.model.service.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.ITEM_EDIT_PAGE;
import static com.alex.musicart.controller.command.PagePath.ORDER_EDIT_PAGE;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.ParameterName.ITEM_IN_STOCK;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class OrderEditCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final OrderServiceImpl orderService = OrderServiceImpl.getInstance();
    private final PaymentTypeServiceImpl paymentTypeService = PaymentTypeServiceImpl.getInstance();
    private final OrderStatusServiceImpl orderStatusService = OrderStatusServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Order order;
        List<Order> orders = (List<Order>) session.getAttribute(ORDERS);
        order = orders.get(0);
        try {
            String statusName = request.getParameter(ORDER_STATUS);
            String paymentTypeName = request.getParameter(ORDER_PAYMENT_TYPE);
            String address = request.getParameter(ORDER_ADDRESS);
            short statusId = 0;
            short paymentTypeId = 0;
            Optional<OrderStatus> optionalStatus = orderStatusService.findOrderStatusByName(statusName);
            if (optionalStatus.isPresent()) {
                statusId = optionalStatus.get().getOrderStatusId();
            }
            Optional<PaymentType> optionalPaymentType = paymentTypeService.findPaymentTypeByName(paymentTypeName);
            if (optionalPaymentType.isPresent()) {
                paymentTypeId = optionalPaymentType.get().getPaymentTypeId();
            }
            orderService.updateOrder(order, statusId, paymentTypeId, address);
            session.setAttribute(ORDER_UPDATE_RESULT, true);
            session.setAttribute(CURRENT_PAGE, ORDER_EDIT_PAGE);
            router.setPagePath(ORDER_EDIT_PAGE);
            router.setRoute(Router.RouteType.FORWARD);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not delete this order.");
            throw new CommandException("Could not delete this order.", e);
        }
    }
}