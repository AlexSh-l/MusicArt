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

import static com.alex.musicart.controller.command.PagePath.ITEM_EDIT_PAGE;
import static com.alex.musicart.controller.command.ParameterName.*;
import static com.alex.musicart.controller.command.SessionAttributeName.*;

public class EditItemCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private final ItemServiceImpl itemService = ItemServiceImpl.getInstance();
    private final CategoryServiceImpl categoryService = CategoryServiceImpl.getInstance();
    private final SubcategoryServiceImpl subcategoryService = SubcategoryServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        HttpSession session = request.getSession();
        Item item;
        long itemId = Long.parseLong(request.getParameter(ITEM_ID));
        try {
            Optional<Item> optionalItem = itemService.findItemById(itemId);
            if (optionalItem.isPresent()) {
                item = optionalItem.get();
                String name = request.getParameter(ITEM_NAME);
                String description = request.getParameter(ITEM_DESCRIPTION);
                String categoryName = request.getParameter(ITEM_CATEGORY);
                Optional<Category> optionalCategory = categoryService.findCategoryByName(categoryName);
                if (optionalCategory.isPresent()) {
                    String subcategoryName = request.getParameter(ITEM_SUBCATEGORY);
                    Optional<Subcategory> optionalSubcategory = subcategoryService.findSubcategoryByName(subcategoryName);
                    if (optionalSubcategory.isPresent()) {
                        double priceInDouble = Double.parseDouble(request.getParameter(ITEM_PRICE));
                        BigDecimal price = BigDecimal.valueOf(priceInDouble);
                        String isItemInStock = request.getParameter(ITEM_IN_STOCK);
                        boolean itemInStock = true;
                        if (isItemInStock == null) {
                            itemInStock = false;
                        }
                        int subcategoryId = optionalSubcategory.get().getSubcategoryId();
                        itemService.updateItem(item, name, subcategoryId, description, price, itemInStock);
                        session.setAttribute(ITEM_UPDATE_RESULT, true);
                        session.setAttribute(CURRENT_PAGE, ITEM_EDIT_PAGE);
                        router.setPagePath(ITEM_EDIT_PAGE);
                        router.setRoute(Router.RouteType.REDIRECT);
                        return router;
                    }
                }
            }
            session.setAttribute(ITEM_UPDATE_RESULT, "Unable to update this item.");
            session.setAttribute(CURRENT_PAGE, ITEM_EDIT_PAGE);
            router.setPagePath(ITEM_EDIT_PAGE);
            router.setRoute(Router.RouteType.FORWARD);
            return router;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Could not delete this item.");
            throw new CommandException("Could not delete this item.", e);
        }
    }
}
