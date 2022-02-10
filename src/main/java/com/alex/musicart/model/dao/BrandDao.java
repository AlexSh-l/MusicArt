package com.alex.musicart.model.dao;

import com.alex.musicart.exception.DaoException;
import com.alex.musicart.model.entity.Brand;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BrandDao extends RootDao {

    List<Brand> findAllBrands() throws DaoException;

    Optional<Brand> findBrandByName(String name) throws DaoException;

    boolean updateBrandName(long id, String name) throws DaoException;
}
