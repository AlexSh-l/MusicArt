package com.alex.musicart.controller.permission;

import java.util.Set;

import static com.alex.musicart.controller.command.PagePath.*;

public enum PagePermission {

    ADMIN(Set.of(INDEX_PAGE,
          MAIN_PAGE,
          ERROR_404_PAGE,
          ERROR_500_PAGE,
          ITEM_EDIT_PAGE,
          ORDER_EDIT_PAGE,
          ADD_ITEM_PAGE,
          ORDERS_PAGE)),

    CLIENT(Set.of(INDEX_PAGE,
          MAIN_PAGE,
          ERROR_404_PAGE,
          ERROR_500_PAGE,
          CART_PAGE,
          ORDER_PAGE)),

    GUEST(Set.of(INDEX_PAGE,
          MAIN_PAGE,
          ERROR_404_PAGE,
          ERROR_500_PAGE,
          SIGN_IN_PAGE,
          REGISTRATION_PAGE));

    private final Set<String> allowedPages;

    PagePermission(Set<String> allowedPages){
        this.allowedPages = allowedPages;
    }

    public Set<String> getAllowedPages(){
        return allowedPages;
    }
}
