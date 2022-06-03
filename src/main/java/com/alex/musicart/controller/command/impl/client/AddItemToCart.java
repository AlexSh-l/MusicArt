package com.alex.musicart.controller.command.impl.client;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Cart;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;
import static com.alex.musicart.controller.command.ParameterName.ITEM_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class AddItemToCart implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        long itemId = Long.parseLong(request.getParameter(ITEM_ID));
        try {
            Optional<Item> optionalItem = itemService.findItemById(itemId);
            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                cart.add(item);
                session.setAttribute(CART, cart);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "An error has occurred while loading item.");
            throw new CommandException("An error has occurred while loading item.", e);
        }
        session.setAttribute(CURRENT_PAGE, MAIN_PAGE);
        router.setPagePath(MAIN_PAGE);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
