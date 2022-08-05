package com.alex.musicart.model.entity;

public class Subcategory extends CustomEntity {

    private int subcategoryId;
    private String name;
    private int categoryId;

    public Subcategory() {
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subcategory that = (Subcategory) o;
        if (subcategoryId != that.subcategoryId) return false;
        if (categoryId != that.categoryId) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = subcategoryId;
        result = 31 * result + name.hashCode();
        result = 31 * result + categoryId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subcategory{");
        sb.append("subcategoryId=").append(subcategoryId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", categoryId=").append(categoryId);
        sb.append('}');
        return sb.toString();
    }
}
