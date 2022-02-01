package com.alex.musicart.controller.command;

import com.alex.musicart.controller.command.impl.general.NonExistentCommand;
import com.alex.musicart.controller.command.impl.general.ToMainCommand;
import com.alex.musicart.controller.command.impl.guest.ToSignInCommand;
import com.alex.musicart.model.entity.User;

import java.util.EnumSet;

import static com.alex.musicart.model.entity.User.UserRole.ADMIN;
import static com.alex.musicart.model.entity.User.UserRole.CLIENT;

public enum CommandType {
    /* general commands */
    NON_EXISTENT(new NonExistentCommand(), EnumSet.of(ADMIN, CLIENT)),
    //CHANGE_LOCALE(new ChangeLocaleCommand(), EnumSet.of(ADMIN, CUSTOMER, GUEST)),
    TO_MAIN(new ToMainCommand(), EnumSet.of(ADMIN, CLIENT)),
    /*LOG_OUT(new LogOutCommand(), EnumSet.of(ADMIN, CUSTOMER)),
    SHOW_ALL_GOODS(new ShowAllGoodsCommand(), EnumSet.of(ADMIN, CUSTOMER, GUEST)),
    GO_TO_ABOUT_US(new GoToAboutUsCommand(), EnumSet.of(ADMIN, CUSTOMER, GUEST)),*/


    /* guest commands */
    /*GO_TO_REGISTRATION(new GoToRegistrationCommand(), EnumSet.of(GUEST)),
    REGISTRATION(new RegistrationCommand(), EnumSet.of(GUEST)),*/
    SIGN_IN(new ToSignInCommand(), EnumSet.of(CLIENT));


    /* customer commands */
    /*SHOW_BASKET(new ShowBasketCommand(), EnumSet.of(CUSTOMER)),
    RECOUNT_ORDER_WHILE_ADDING_ITEM(new RecountOrderWhileAddingItem(), EnumSet.of(CUSTOMER)),
    RECOUNT_ORDER_WHILE_REMOVING_ITEM(new RecountOrderWhileRemovingItem(), EnumSet.of(CUSTOMER)),
    ADD_ITEM_TO_BASKET(new AddItemToBasketCommand(), EnumSet.of(CUSTOMER));*/

    //CHANGE_PERSONAL_DATA_COMMAND


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
