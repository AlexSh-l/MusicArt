package com.alex.musicart.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

    private static final Logger logger = LogManager.getLogger();

    private CommandFactory() {
    }

    public static Command getCommand(String commandName) {
        Command command = CommandType.NON_EXISTENT.getCurrentCommand();
        try {
            if (commandName != null) {
                CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
                command = commandType.getCurrentCommand();
            }
        } catch (IllegalArgumentException e) {
            logger.error("The command {} doesn't exist:", commandName, e);
        }
        return command;
    }
}
