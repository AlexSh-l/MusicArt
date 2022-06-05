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
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;
import static com.alex.musicart.controller.command.ParameterName.LANGUAGE;
import static com.alex.musicart.controller.command.ParameterName.PAGE_NUMBER;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class ToMainCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(ITEM_UPDATE_RESULT);
        session.removeAttribute(ORDER_UPDATE_RESULT);
        session.removeAttribute(REGISTRATION_RESULT);
        Router router = new Router();
        String language = request.getParameter(LANGUAGE);
        int pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER));
        long itemId = 0;
        String locale = (String) session.getAttribute(LOCALE);
        if ((language == null) && (locale == null)) {
            session.setAttribute(LOCALE, "en_EN");
        }
        List<Item> items;
        try {
            if (pageNumber > 1) {
                Optional<Long> lastItemIdFromPreviousPage = itemService.findLastItemByIdWithSetAmount((pageNumber - 1) * 12);
                if (lastItemIdFromPreviousPage.isPresent()) {
                    itemId = lastItemIdFromPreviousPage.get();
                }
            }
            items = itemService.findSetAmountOfItemsById(itemId, 12);
            items = itemService.findImagesForSetItems(items);
            List<Item> itemsAmount = itemService.findSetAmountOfItemsById(itemId, 12 * 3);
            int nextItems = itemsAmount.size() - 12;
            session.setAttribute(ITEMS, items);
            session.setAttribute(NEXT_PAGES, nextItems);
            session.setAttribute(CURRENT_PAGE_NUMBER, pageNumber);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "An error has occurred while loading items.");
            throw new CommandException("An error has occurred while loading items.", e);
        }
        session.setAttribute(CURRENT_PAGE, MAIN_PAGE);
        router.setPagePath(MAIN_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
