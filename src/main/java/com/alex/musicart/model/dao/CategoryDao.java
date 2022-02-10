package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Category;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoryDao extends RootDao {

    List<Category> findAllCategories() throws DaoException;

    Optional<Category> findCategoryByName(String category) throws DaoException;

    Optional<Category> findCategoryById(int categoryId) throws DaoException;

    boolean updateCategoryName(long id, String name) throws DaoException;
}
