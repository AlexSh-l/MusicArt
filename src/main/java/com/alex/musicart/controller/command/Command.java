package com.alex.musicart.controller.command;

import com.alex.musicart.controller.Router;
import com.alex.musicart.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
