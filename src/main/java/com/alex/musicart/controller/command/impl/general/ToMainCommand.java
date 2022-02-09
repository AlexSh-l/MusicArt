package com.alex.musicart.controller.command.impl.general;

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

import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.ITEMS;
import static com.alex.musicart.controller.command.SessionAttributeName.SIGN_IN_RESULT;

public class ToMainCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();
        List<Item> items;
        try {
            items = itemService.findAllItems();
            session.setAttribute(ITEMS, items);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "An error has occurred while loading items.");
            throw new CommandException("An error has occurred while loading items.", e);
        }
        router.setPagePath(MAIN_PAGE);
        return router;
    }
}
