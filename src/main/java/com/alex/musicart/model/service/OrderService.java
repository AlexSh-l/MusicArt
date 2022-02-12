package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.Order;

public interface OrderService {
    boolean createOrder(Order order) throws ServiceException;
}
