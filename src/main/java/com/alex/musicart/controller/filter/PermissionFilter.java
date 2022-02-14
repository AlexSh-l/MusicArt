package com.alex.musicart.controller.filter;

import com.alex.musicart.controller.command.CommandType;
import com.alex.musicart.controller.permission.CommandPermission;
import com.alex.musicart.controller.permission.PagePermission;
import com.alex.musicart.model.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static com.alex.musicart.controller.command.PagePath.ERROR_404_PAGE;
import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;
import static com.alex.musicart.controller.command.ParameterName.COMMAND;
import static com.alex.musicart.controller.command.SessionAttributeName.USER;

@WebFilter(urlPatterns = {"/*"})
public class PermissionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getServletPath();
        User.UserRole userRole = User.UserRole.GUEST;
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            userRole = user.getRole();
        }
        if (requestURI.equals("/controller")) {
            String command = httpRequest.getParameter(COMMAND);
            if (command == null) {
                servletRequest.getRequestDispatcher(ERROR_404_PAGE).forward(httpRequest, httpResponse);
                return;
            } else {
                Set<String> commands;
                switch (userRole) {
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
                boolean isCommandExist = Arrays.stream(CommandType.values())
                        .anyMatch(commandType -> command.equalsIgnoreCase(commandType.toString()));
                if (isCommandExist && !commands.contains(command.toUpperCase())) {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                if (!commands.contains(command.toUpperCase())) {
                    servletRequest.getRequestDispatcher(ERROR_404_PAGE).forward(httpRequest, httpResponse);
                    return;
                }
            }
        } else {
            boolean isPageAllowed;
            Set<String> pages;
            switch (userRole) {
                case ADMIN:
                    pages = PagePermission.ADMIN.getAllowedPages();

                    isPageAllowed = pages.stream().anyMatch(requestURI::contains);
                    break;
                case CLIENT:
                    pages = PagePermission.CLIENT.getAllowedPages();

                    isPageAllowed = pages.stream().anyMatch(requestURI::contains);
                    break;
                default:
                    pages = PagePermission.GUEST.getAllowedPages();
                    isPageAllowed = pages.stream().anyMatch(requestURI::contains);
                    break;
            }
            if (!isPageAllowed && user == null) {
                user = new User();
                user.setRole(User.UserRole.GUEST);
                session.setAttribute(USER, user);
                httpResponse.sendRedirect(MAIN_PAGE);
                return;
            } else if (!isPageAllowed) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
