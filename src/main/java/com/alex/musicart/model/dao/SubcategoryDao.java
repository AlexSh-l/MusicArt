package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Subcategory;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SubcategoryDao extends RootDao {

    List<Subcategory> findAllSubcategories() throws DaoException;

    Optional<Subcategory> findSubcategoryByName(String subcategory) throws DaoException;

    Optional<Subcategory> findSubcategoryById(int subcategoryId) throws DaoException;

    List<Subcategory> findSubcategoriesOfCategory(int categoryId) throws DaoException;

    boolean updateSubcategoryName(long id, String name) throws DaoException;
}
