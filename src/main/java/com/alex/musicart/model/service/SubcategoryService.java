package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Subcategory;

import java.util.Optional;

public interface SubcategoryService {
    Optional<Subcategory> findSubcategoryByName(String name) throws ServiceException;
}
