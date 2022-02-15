package com.alex.musicart.model.entity;

import java.math.BigDecimal;

public class Item extends CustomEntity {

    private long itemId;
    private String name;
    private String category;
    private String subcategory;
    private int subcategoryId;
    private String description;
    private BigDecimal price;
    private boolean inStock;

    public Item() {
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        if (itemId != item.itemId) return false;
        if (subcategoryId != item.subcategoryId) return false;
        if (inStock != item.inStock) return false;
        if (!name.equals(item.name)) return false;
        if (category != null ? !category.equals(item.category) : item.category != null) return false;
        if (subcategory != null ? !subcategory.equals(item.subcategory) : item.subcategory != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        return price != null ? price.equals(item.price) : item.price == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (subcategory != null ? subcategory.hashCode() : 0);
        result = 31 * result + subcategoryId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (inStock ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("itemId=").append(itemId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", subcategory='").append(subcategory).append('\'');
        sb.append(", subcategoryId=").append(subcategoryId);
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", inStock=").append(inStock);
        sb.append('}');
        return sb.toString();
    }
}
