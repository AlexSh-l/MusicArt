package com.alex.musicart.controller.command;

import com.alex.musicart.controller.command.impl.general.NonExistentCommand;
import com.alex.musicart.controller.command.impl.general.ToMainCommand;
import com.alex.musicart.controller.command.impl.guest.*;
import com.alex.musicart.model.entity.User;

import java.util.EnumSet;

import static com.alex.musicart.model.entity.User.UserRole.ADMIN;
import static com.alex.musicart.model.entity.User.UserRole.CLIENT;

public enum CommandType {
    /* general commands */
    NON_EXISTENT(new NonExistentCommand(), EnumSet.of(ADMIN, CLIENT)),

    TO_MAIN(new ToMainCommand(), EnumSet.of(ADMIN, CLIENT)),

    TO_SIGN_IN(new ToSignInCommand(), EnumSet.of(CLIENT)),
    SIGN_IN(new SignInCommand(), EnumSet.of(CLIENT)),
    REGISTER(new RegisterCommand(), EnumSet.of(CLIENT)),
    TO_REGISTRATION(new ToRegistrationCommand(), EnumSet.of(CLIENT)),
    ITEM_SEARCH(new ItemSearchCommand(), EnumSet.of(CLIENT));
    /* customer commands */





    /* admin commands */


    private Command command;
    private EnumSet<User.UserRole> allowedRoles;

    CommandType(Command command, EnumSet<User.UserRole> allowedRoles) {
        this.command = command;
        this.allowedRoles = allowedRoles;
    }

    public Command getCurrentCommand() {
        return command;
    }

    public EnumSet<User.UserRole> getAllowedRoles() {
        return allowedRoles;
    }
}
