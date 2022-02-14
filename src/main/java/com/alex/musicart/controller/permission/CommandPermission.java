package com.alex.musicart.controller.permission;

import com.alex.musicart.controller.command.CommandType;

import java.util.Set;

public enum CommandPermission {

    ADMIN(Set.of(CommandType.NON_EXISTENT.name(),
            CommandType.TO_MAIN.name(),
            CommandType.SIGN_OUT.name(),
            CommandType.ITEM_SEARCH.name(),
            CommandType.TO_ITEM_EDIT.name(),
            CommandType.ADD_ITEM.name(),
            CommandType.TO_ADD_ITEM.name(),
            CommandType.TO_ORDERS.name(),
            CommandType.TO_ORDER_EDIT.name(),
            CommandType.DELETE_ORDER.name(),
            CommandType.TO_ORDER_ITEMS.name(),
            CommandType.CHANGE_LOCALE.name(),
            CommandType.EDIT_ITEM.name(),
            CommandType.EDIT_ORDER.name(),
            CommandType.DELETE_ITEM.name())),

    CLIENT(Set.of(CommandType.NON_EXISTENT.name(),
            CommandType.TO_MAIN.name(),
            CommandType.SIGN_OUT.name(),
            CommandType.ITEM_SEARCH.name(),
            CommandType.ADD_TO_CART.name(),
            CommandType.TO_CART.name(),
            CommandType.REMOVE_FROM_CART.name(),
            CommandType.CONFIRM_ORDER.name(),
            CommandType.TO_ORDER.name(),
            CommandType.CHANGE_LOCALE.name())),

    GUEST(Set.of(CommandType.NON_EXISTENT.name(),
            CommandType.TO_MAIN.name(),
          CommandType.TO_SIGN_IN.name(),
          CommandType.SIGN_IN.name(),
          CommandType.TO_REGISTRATION.name(),
          CommandType.REGISTER.name(),
          CommandType.ITEM_SEARCH.name(),
          CommandType.CHANGE_LOCALE.name()));

    private final Set<String> allowedCommands;

    CommandPermission(Set<String> allowedCommands){
        this.allowedCommands = allowedCommands;
    }

    public Set<String> getAllowedCommands(){
        return allowedCommands;
    }
}
