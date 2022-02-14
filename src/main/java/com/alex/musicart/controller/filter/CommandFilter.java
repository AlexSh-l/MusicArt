package com.alex.musicart.controller.filter;

import com.alex.musicart.controller.command.CommandType;
import com.alex.musicart.controller.permission.CommandPermission;
import com.alex.musicart.model.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static com.alex.musicart.controller.command.PagePath.ERROR_404_PAGE;
import static com.alex.musicart.controller.command.ParameterName.COMMAND;
import static com.alex.musicart.controller.command.SessionAttributeName.USER;

//@WebFilter(urlPatterns = {"/controller*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE})
public class CommandFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession();



        String command = httpServletRequest.getParameter(COMMAND);
        /*if (command == null){
            servletRequest.getRequestDispatcher(ERROR_404_PAGE).forward(httpServletRequest,httpServletResponse);
            return;
        }*/
        if(command != null) {
            User.UserRole role = User.UserRole.GUEST;
            Set<String> commands;
            User user = (User) session.getAttribute(USER);
            if (user != null) {
                role = user.getRole();
            }
            switch (role) {
                case ADMIN:
                    commands = CommandPermission.ADMIN.getAllowedCommands();
                    break;
                case CLIENT:
                    commands = CommandPermission.CLIENT.getAllowedCommands();
                    break;
                default:
                    commands = CommandPermission.GUEST.getAllowedCommands();
                    break;
            }

            boolean isCorrect = Arrays.stream(CommandType.values())
                    .anyMatch(commandType -> command.equalsIgnoreCase(commandType.toString()));

            if (isCorrect && !commands.contains(command.toUpperCase())) {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            if (!commands.contains(command.toUpperCase())) {

                servletRequest.getRequestDispatcher(ERROR_404_PAGE)
                        .forward(httpServletRequest, httpServletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
