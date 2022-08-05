package com.alex.musicart.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Category extends CustomEntity {

    private int categoryId;
    private String name;
    private List<Subcategory> subcategories = new ArrayList<>();

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public void addSubcategory(Subcategory subcategory) {
        subcategories.add(subcategory);
    }

    public void removeSubcategory(Subcategory subcategory) {
        subcategories.remove(subcategory);
    }

    public void clearSubcategories() {
        subcategories.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        if (categoryId != category.categoryId) return false;
        if (!name.equals(category.name)) return false;
        return subcategories.equals(category.subcategories);
    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + name.hashCode();
        result = 31 * result + subcategories.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("categoryId=").append(categoryId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", subcategories=").append(subcategories);
        sb.append('}');
        return sb.toString();
    }
}
