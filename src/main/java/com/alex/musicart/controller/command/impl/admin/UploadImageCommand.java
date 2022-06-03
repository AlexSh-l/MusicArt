package com.alex.musicart.controller.command.impl.admin;

import com.alex.musicart.controller.Router;
import com.alex.musicart.controller.command.Command;
import com.alex.musicart.exception.CommandException;
import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Item;
import com.alex.musicart.model.service.ItemService;
import com.alex.musicart.model.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.alex.musicart.controller.command.PagePath.*;
import static com.alex.musicart.controller.command.ParameterName.ITEM_ID;
import static com.alex.musicart.controller.command.SessionAttributeName.ITEMS;

public class UploadImageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ABSOLUTE_PATH = "D:\\EpamCoarses\\MusicArt\\src\\main\\webapp\\images\\";
    private final ItemService itemService = ItemServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        try (InputStream inputStream = request.getPart("img").getInputStream()) {
            String fileName = request.getPart("img").getSubmittedFileName();
            String path = ABSOLUTE_PATH + fileName;
            Path uploadPath = new File(path).toPath();
            Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            logger.log(Level.INFO, "Image successfully uploaded. Image name: " + fileName);
            String contextPath = request.getContextPath();
            String imagePath = contextPath + "/images/" + fileName;
            HttpSession session = request.getSession();
            List<Item> items = (List<Item>) session.getAttribute(ITEMS);
            long itemId = Long.parseLong(request.getParameter(ITEM_ID));
            boolean isImageAddedToDatabase = itemService.addNewImage(itemId, fileName, imagePath);
            if (isImageAddedToDatabase) {
                items = itemService.findImagesForSetItems(items);
                session.setAttribute(ITEMS, items);
            }
            router.setPagePath(MAIN_PAGE);
            router.setRoute(Router.RouteType.REDIRECT);
            return router;
        } catch (IOException | ServletException | ServiceException e) {
            logger.log(Level.ERROR, "Upload photo failed.");
            throw new CommandException("Upload photo failed.", e);
        }
    }
}
