package com.alex.musicart.controller;

import com.alex.musicart.controller.command.Command;
import com.alex.musicart.controller.command.CommandFactory;
import com.alex.musicart.controller.command.ParameterName;
import com.alex.musicart.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(ParameterName.COMMAND);
        Command command = CommandFactory.getCommand(commandName);
        Router router;
        try {
            router = command.execute(request);
            switch (router.getRoute()) {
                case FORWARD: {
                    request.getRequestDispatcher(router.getPagePath()).forward(request, response);
                    break;
                }
                case REDIRECT: {
                    response.sendRedirect(request.getContextPath() + router.getPagePath());
                    break;
                }
            }
        } catch (CommandException e) {
            logger.error("Internal error occurred", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
