package com.alex.musicart.controller.filter;

import com.alex.musicart.controller.permission.PagePermission;
import com.alex.musicart.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static com.alex.musicart.controller.command.PagePath.MAIN_PAGE;
import static com.alex.musicart.controller.command.SessionAttributeName.USER;

//@WebFilter(urlPatterns = {"/*"})
public class PageFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        //String currentPage = httpRequest.getRequestURL().toString();

        String requestURI = httpRequest.getServletPath();
        String query = ((HttpServletRequest) servletRequest).getQueryString();
        //logger.log(Level.INFO,"Page URI: " + requestURI);

        User.UserRole userRole = User.UserRole.GUEST;
        User user = (User) session.getAttribute(USER);
        if(user != null){
            userRole = user.getRole();
            //logger.log(Level.INFO, userRole.toString());
        }
        //logger.log(Level.INFO,userRole);
        boolean isCorrect;
        Set<String> pages;
        switch (userRole){
            case ADMIN :
                pages = PagePermission.ADMIN.getAllowedPages();
                //logger.log(Level.INFO,pages);
                isCorrect = pages.stream().anyMatch(requestURI::contains);
                break;
            case CLIENT :
                pages = PagePermission.CLIENT.getAllowedPages();
                //logger.log(Level.INFO,pages);
                isCorrect = pages.stream().anyMatch(requestURI::contains);
                break;
            default :
                pages = PagePermission.GUEST.getAllowedPages();
                isCorrect = pages.stream().anyMatch(requestURI::contains);
                break;
        }
        if(!isCorrect && user == null){
            user = new User();
            user.setRole(User.UserRole.GUEST);
            session.setAttribute(USER, user);
            httpResponse.sendRedirect(MAIN_PAGE);
            return;
        }else if(!isCorrect){
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
