package com.alex.musicart.controller.command;

import com.alex.musicart.controller.command.impl.admin.AddItemCommand;
import com.alex.musicart.controller.command.impl.admin.ToAddItemCommand;
import com.alex.musicart.controller.command.impl.admin.ToEditItemCommand;
import com.alex.musicart.controller.command.impl.client.*;
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
    SIGN_OUT(new SignOutCommand(), EnumSet.of(ADMIN, CLIENT)),
    REGISTER(new RegisterCommand(), EnumSet.of(CLIENT)),
    TO_REGISTRATION(new ToRegistrationCommand(), EnumSet.of(CLIENT)),
    ITEM_SEARCH(new ItemSearchCommand(), EnumSet.of(CLIENT)),
    ADD_TO_CART(new AddItemToCart(), EnumSet.of(CLIENT)),
    TO_CART(new ToCart(), EnumSet.of(CLIENT)),
    REMOVE_FROM_CART(new RemoveItemFromCart(), EnumSet.of(CLIENT)),
    TO_ITEM_EDIT(new ToEditItemCommand(), EnumSet.of(CLIENT)),
    ADD_ITEM(new AddItemCommand(), EnumSet.of(CLIENT)),
    TO_ADD_ITEM(new ToAddItemCommand(), EnumSet.of(CLIENT)),
    CONFIRM_ORDER(new ConfirmOrderCommand(), EnumSet.of(CLIENT)),
    TO_ORDER(new ToOrderCommand(), EnumSet.of(CLIENT));
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
