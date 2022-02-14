package com.alex.musicart.controller.command.impl.client;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.*;
import com.alex.musicart.model.service.OrderService;
import com.alex.musicart.model.service.OrderStatusService;
import com.alex.musicart.model.service.impl.ItemServiceImpl;
import com.alex.musicart.model.service.impl.OrderServiceImpl;
import com.alex.musicart.model.service.impl.OrderStatusServiceImpl;
import com.alex.musicart.model.service.impl.PaymentTypeServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.CART;

public class ConfirmOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final PaymentTypeServiceImpl paymentTypeService = PaymentTypeServiceImpl.getInstance();
    private final OrderStatusService orderStatusService = OrderStatusServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        List<Item> items = cart.getItems();
        Order order = new Order();
        String address = request.getParameter(ADDRESS);
        String paymentTypeName = request.getParameter(PAYMENT_TYPE);
        //long itemId = Long.parseLong(request.getParameter(ITEM_ID));
        boolean isOrderEmpty = address.isEmpty() && paymentTypeName.isEmpty();
        try {
            if(paymentTypeName.equals("cash")){
                paymentTypeName = "наличными";
            }else if(paymentTypeName.equals("card")){
                paymentTypeName = "картой";
            }
            if(!address.isEmpty()) {
                Optional<PaymentType> optionalPaymentType = paymentTypeService.findPaymentTypeByName(paymentTypeName);
                if (optionalPaymentType.isPresent()) {
                    PaymentType paymentType = optionalPaymentType.get();
                    order.setAddress(address);
                    order.setPaymentTypeId(paymentType.getPaymentTypeId());
                    Optional<OrderStatus> optionalOrderStatus = orderStatusService.findOrderStatusByName("принят");
                    if (optionalOrderStatus.isPresent()) {
                        OrderStatus orderStatus = optionalOrderStatus.get();
                        //order.setStatus(orderStatus.get);
                        order.setStatusId(orderStatus.getOrderStatusId());
                        order.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
                        User user = (User) session.getAttribute(USER);
                        order.setUserId(user.getUserId());
                        order.setUser(user);
                        double totalPrice = 0.0;
                        for (Item item : items) {
                            totalPrice += item.getPrice().doubleValue();
                        }
                        order.setPrice(BigDecimal.valueOf(totalPrice));
                        order.setItems(items);
                        if (orderService.createOrder(order)) {
                            session.setAttribute(ORDER_RESULT, true);
                            session.removeAttribute(CART);
                        } else {
                            session.setAttribute(ORDER_RESULT, "Unable to create an order.");
                        }
                    } else {
                        session.setAttribute(ORDER_RESULT, "This order status does not exist.");
                    }
                } else {
                    session.setAttribute(ORDER_RESULT, "This payment type does not exist.");
                }
            }else {
                session.setAttribute(ORDER_RESULT, "Please, fill in the address.");
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "An error has occurred while loading item.");
            throw new CommandException("An error has occurred while loading item.", e);
        }
        session.setAttribute(CURRENT_PAGE, ORDER_PAGE);
        router.setPagePath(ORDER_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
