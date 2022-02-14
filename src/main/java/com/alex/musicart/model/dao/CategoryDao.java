package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends RootDao {

    List<Category> findAllCategories() throws DaoException;

    Optional<Category> findCategoryByName(String category) throws DaoException;

    Optional<Category> findCategoryById(int categoryId) throws DaoException;

    boolean updateCategoryName(long id, String name) throws DaoException;
}
