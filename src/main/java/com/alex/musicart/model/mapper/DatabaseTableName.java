package com.alex.musicart.model.mapper;

public final class DatabaseTableName {
    public static final String USERS_ID = "users.us_id";
    public static final String USERS_NAME = "users.us_name";
    public static final String USERS_LOGIN = "users.us_login";
    public static final String USERS_PASSWORD = "users.us_password";
    public static final String USERS_EMAIL = "users.us_email";
    public static final String USERS_PHONE = "users.us_phone";
    public static final String USERS_ROLE_ID = "users.us_role_id";

    public static final String ITEMS_ID = "items.it_id";
    public static final String ITEMS_NAME = "items.it_name";
    public static final String ITEMS_SUBCATEGORY_ID = "items.it_subcategory_id";
    public static final String ITEMS_DESCRIPTION = "items.it_description";
    public static final String ITEMS_PRICE = "items.it_price";
    public static final String ITEMS_IS_IN_STOCK = "items.it_is_in_stock";

    public static final String BRANDS_ID = "brands.br_id";
    public static final String BRANDS_NAME = "brands.br_name";

    public static final String SUBCATEGORIES_ID = "subcategories.su_id";
    public static final String SUBCATEGORIES_NAME = "subcategories.su_name";
    public static final String SUBCATEGORIES_CATEGORY_ID = "subcategories.su_category_id";

    public static final String CATEGORIES_ID = "categories.ca_id";
    public static final String CATEGORIES_NAME = "categories.ca_name";


    private DatabaseTableName(){
    }
}
