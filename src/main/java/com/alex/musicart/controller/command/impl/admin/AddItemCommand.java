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
import java.util.Optional;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class AddItemCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();
    private final SubcategoryServiceImpl subcategoryService = SubcategoryServiceImpl.getInstance();
    private final CategoryServiceImpl categoryService = CategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Item item = new Item();
        String name = request.getParameter(ITEM_NAME);
        String description = request.getParameter(ITEM_DESCRIPTION);
        String categoryName = request.getParameter(ITEM_CATEGORY);
        String subcategoryName = request.getParameter(ITEM_SUBCATEGORY);
        double priceInDouble = Double.parseDouble(request.getParameter(ITEM_PRICE));
        BigDecimal price = BigDecimal.valueOf(priceInDouble);
        String isItemInStock = request.getParameter(ITEM_IN_STOCK);
        boolean itemInStock = true;
        if (isItemInStock == null) {
            itemInStock = false;
        }
        try {
            Optional<Category> optionalCategory = categoryService.findCategoryByName(categoryName);
            if (optionalCategory.isPresent()) {
                Optional<Subcategory> optionalSubcategory = subcategoryService.findSubcategoryByName(subcategoryName);
                if (optionalSubcategory.isPresent()) {
                    int subcategoryId = optionalSubcategory.get().getSubcategoryId();
                    item.setName(name);
                    item.setDescription(description);
                    item.setSubcategoryId(subcategoryId);
                    item.setPrice(price);
                    item.setInStock(itemInStock);
                    itemService.createNewItem(item);
                    session.setAttribute(ITEM_CREATION_RESULT, true);
                } else {
                    session.setAttribute(ITEM_CREATION_RESULT, "This subcategory does not exist.");
                }
            } else {
                session.setAttribute(ITEM_CREATION_RESULT, "This category does not exist.");
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not add this item.");
            throw new CommandException("Could not add this item.", e);
        }
        session.setAttribute(CURRENT_PAGE, ADD_ITEM_PAGE);
        router.setPagePath(ADD_ITEM_PAGE);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
