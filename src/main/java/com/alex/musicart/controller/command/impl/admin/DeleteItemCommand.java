package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Category;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.entity.Subcategory;
import com.alex.musicart.model.service.impl.CategoryServiceImpl;
import com.alex.musicart.model.service.impl.ItemServiceImpl;
import com.alex.musicart.model.service.impl.SubcategoryServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.ADD_ITEM_PAGE;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.ParameterName.ITEM_IN_STOCK;
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
        item = items.get(1);

        session.setAttribute(CURRENT_PAGE, ADD_ITEM_PAGE);
        router.setPagePath(ADD_ITEM_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
