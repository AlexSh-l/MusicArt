package com.alex.musicart.controller.filter;

import com.alex.musicart.model.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.alex.musicart.controller.command.SessionAttributeName.USER;

@WebFilter(urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE})
public class UserRegistrationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        //var user = (User) session.getAttribute(USER);

        /*Optional<Object> optionalUser = Optional.ofNullable(session.getAttribute(USER));
        if (!optionalUser.isPresent()) {
            User user = new User();
            user.setRole(User.UserRole.CLIENT);
            session.setAttribute(USER, user);
        }*/
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
