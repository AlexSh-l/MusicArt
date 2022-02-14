package com.alex.musicart.controller.command;

import com.alex.musicart.controller.command.impl.admin.*;
import com.alex.musicart.controller.command.impl.client.*;
import com.alex.musicart.controller.command.impl.general.ChangeLocaleCommand;
import com.alex.musicart.controller.command.impl.general.NonExistentCommand;
import com.alex.musicart.controller.command.impl.general.ToMainCommand;
import com.alex.musicart.controller.command.impl.guest.*;

public enum CommandType {

    /* admin commands */
    ADD_ITEM(new AddItemCommand()),
    DELETE_ITEM(new DeleteItemCommand()),
    DELETE_ORDER(new DeleteOrderCommand()),
    EDIT_ITEM(new EditItemCommand()),
    EDIT_ORDER(new OrderEditCommand()),
    TO_ADD_ITEM(new ToAddItemCommand()),
    TO_ITEM_EDIT(new ToEditItemCommand()),
    TO_ORDER_EDIT(new ToOrderEditCommand()),
    TO_ORDER_ITEMS(new ToOrderItemsCommand()),
    TO_ORDERS(new ToOrdersCommand()),

    /* client commands */
    ADD_TO_CART(new AddItemToCart()),
    CONFIRM_ORDER(new ConfirmOrderCommand()),
    REMOVE_FROM_CART(new RemoveItemFromCart()),
    TO_CART(new ToCart()),
    TO_ORDER(new ToOrderCommand()),

    /* general commands */
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    NON_EXISTENT(new NonExistentCommand()),
    TO_MAIN(new ToMainCommand()),

    /* guest commands */
    ITEM_SEARCH(new ItemSearchCommand()),
    REGISTER(new RegisterCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand()),
    TO_REGISTRATION(new ToRegistrationCommand()),
    TO_SIGN_IN(new ToSignInCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }
}
