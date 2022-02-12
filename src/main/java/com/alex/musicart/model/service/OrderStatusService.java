package com.alex.musicart.model.service;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.OrderStatus;

import java.util.Optional;

public interface OrderStatusService {
    Optional<OrderStatus> findOrderStatusByName(String name) throws ServiceException;
}
