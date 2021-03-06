package com.alex.musicart.controller.command.impl.guest;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class ItemSearchCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.setAttribute(NEXT_PAGES, 0);
        String itemName = request.getParameter(ITEM_NAME);
        try {
            List<Item> items = new ArrayList<>();
            Optional<Item> optionalItem = itemService.findItemByName(itemName);
            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                items.add(item);
                items = itemService.findImagesForSetItems(items);
            }
            session.setAttribute(ITEMS, items);
            session.setAttribute(CURRENT_PAGE, MAIN_PAGE);
            router.setPagePath(MAIN_PAGE);
            router.setRoute(Router.RouteType.FORWARD);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find any items with this name.");
            throw new CommandException("Could not find any items with this name.", e);
        }
    }
}
