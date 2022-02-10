package com.alex.musicart.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart extends CustomEntity {

    private List<Item> items = new ArrayList<>();
    private int cartSize;

    public Cart() {
        this.cartSize = 0;
    }

    public void add(Item item) {
        items.add(item);
        this.cartSize++;
    }

    public void remove(Item item) {
        items.remove(item);
        this.cartSize--;
    }

    public void clear() {
        items.clear();
        this.cartSize = 0;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getCartSize() {
        return cartSize;
    }

    public void setCartSize(int cartSize) {
        this.cartSize = cartSize;
    }
}
