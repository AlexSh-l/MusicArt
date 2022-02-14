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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.ITEM_EDIT_PAGE;
import static com.alex.musicart.controller.command.ParameterName.ITEM_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.CURRENT_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.ITEMS;

public class ToEditItemCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        long itemId = Long.parseLong(request.getParameter(ITEM_ID));
        try {
            Optional<Item> optionalItem = itemService.findItemById(itemId);
            if(optionalItem.isPresent()){
                List<Item> items = new ArrayList<>();
                items.add(optionalItem.get());
                session.setAttribute(ITEMS, items);
            }
            session.setAttribute(CURRENT_PAGE, ITEM_EDIT_PAGE);
            router.setPagePath(ITEM_EDIT_PAGE);
            router.setRoute(Router.RouteType.FORWARD);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not find any items with this id.");
            throw new CommandException("Could not find any items with this id.", e);
        }
    }
}
