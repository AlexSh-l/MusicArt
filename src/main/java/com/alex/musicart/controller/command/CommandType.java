package com.alex.musicart.controller.command;

import com.alex.musicart.controller.command.impl.admin.*;
import com.alex.musicart.controller.command.impl.client.*;
import com.alex.musicart.controller.command.impl.general.ChangeLocaleCommand;
import com.alex.musicart.controller.command.impl.general.NonExistentCommand;
import com.alex.musicart.controller.command.impl.general.ToMainCommand;
import com.alex.musicart.controller.command.impl.guest.*;
import com.alex.musicart.model.entity.User;

import java.util.EnumSet;

public enum CommandType {
    /* general commands */
    NON_EXISTENT(new NonExistentCommand()/*, EnumSet.of(ADMIN, CLIENT)*/),

    TO_MAIN(new ToMainCommand()),

    TO_SIGN_IN(new ToSignInCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand()),
    REGISTER(new RegisterCommand()),
    TO_REGISTRATION(new ToRegistrationCommand()),
    ITEM_SEARCH(new ItemSearchCommand()),
    ADD_TO_CART(new AddItemToCart()),
    TO_CART(new ToCart()),
    REMOVE_FROM_CART(new RemoveItemFromCart()),
    TO_ITEM_EDIT(new ToEditItemCommand()),
    ADD_ITEM(new AddItemCommand()),
    TO_ADD_ITEM(new ToAddItemCommand()),
    CONFIRM_ORDER(new ConfirmOrderCommand()),
    TO_ORDER(new ToOrderCommand()),
    TO_ORDERS(new ToOrdersCommand()),
    TO_ORDER_EDIT(new ToOrderEditCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    TO_ORDER_ITEMS(new ToOrderItemsCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand());
    /* customer commands */





    /* admin commands */


    private Command command;
    private EnumSet<User.UserRole> allowedRoles;

    CommandType(Command command/*, EnumSet<User.UserRole> allowedRoles*/) {
        this.command = command;
        //this.allowedRoles = allowedRoles;
    }

    public Command getCurrentCommand() {
        return command;
    }

    public EnumSet<User.UserRole> getAllowedRoles() {
        return allowedRoles;
    }
}
