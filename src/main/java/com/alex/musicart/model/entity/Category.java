package com.alex.musicart.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Category extends CustomEntity {

    private int categoryId;
    private String name;
    private List<Subcategory> subcategories = new ArrayList<>();

    public Category(){
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

    public void addSubcategory(Subcategory subcategory){
        subcategories.add(subcategory);
    }

    public void removeSubcategory(Subcategory subcategory){
        subcategories.remove(subcategory);
    }

    public void clearSubcategories(){
        subcategories.clear();
    }
}
