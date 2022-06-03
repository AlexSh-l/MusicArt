package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.alex.musicart.controller.command.PagePath.ITEM_EDIT_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class DeleteItemCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Item item;
        List<Item> items = (List<Item>) session.getAttribute(ITEMS);
        item = items.get(0);
        try {
            itemService.deleteItem(item.getItemId());
            session.setAttribute(ITEM_DELETION_RESULT, "Item successfully deleted.");
        } catch (ServiceException e) {
            session.setAttribute(ITEM_DELETION_RESULT, "Could not delete this item.");
            logger.log(Level.ERROR, "Could not delete this item.");
            throw new CommandException("Could not delete this item.", e);
        }
        session.setAttribute(CURRENT_PAGE, ITEM_EDIT_PAGE);
        router.setPagePath(ITEM_EDIT_PAGE);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
