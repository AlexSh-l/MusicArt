package com.alex.musicart.model.service.impl;

import com.alex.musicart.exception.ServiceException;
import com.alex.musicart.model.entity.OrderStatus;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class OrderStatusServiceImplTest {

    private static OrderStatusServiceImpl orderStatusServiceInstance;
    private static OrderStatus expectedOrderStatus;

    @BeforeClass
    public static void initializeExpectedValues() {
        orderStatusServiceInstance = OrderStatusServiceImpl.getInstance();
        expectedOrderStatus = new OrderStatus();
        expectedOrderStatus.setOrderStatusId((short) 2);
        expectedOrderStatus.setName("собирается");

    }

    @Test
    public void findOrderStatusByNameTest() {
        String orderStatusName = "собирается";
        try {
            Optional<OrderStatus> optionalOrderStatus = orderStatusServiceInstance.findOrderStatusByName(orderStatusName);
            if (optionalOrderStatus.isPresent()) {
                OrderStatus actual = optionalOrderStatus.get();
                Assert.assertEquals(expectedOrderStatus, actual);
            } else {
                Assert.fail();
            }
        } catch (ServiceException e) {
            Assert.fail(e.getMessage());
        }
    }
}
