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
        this.cartSize = items.size();
    }

    public void remove(Item item) {
        items.remove(item);
        this.cartSize = items.size();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        if (cartSize != cart.cartSize) return false;
        return items.equals(cart.items);
    }

    @Override
    public int hashCode() {
        int result = items.hashCode();
        result = 31 * result + cartSize;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append("items=").append(items);
        sb.append(", cartSize=").append(cartSize);
        sb.append('}');
        return sb.toString();
    }
}
