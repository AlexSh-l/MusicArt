package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findCategoryByName(String name) throws ServiceException;
}
