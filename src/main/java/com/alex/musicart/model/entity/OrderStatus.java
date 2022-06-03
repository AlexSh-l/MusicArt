package com.alex.musicart.model.entity;

public class OrderStatus extends CustomEntity {
    private short orderStatusId;
    private String name;

    public OrderStatus() {
    }

    public short getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(short paymentTypeId) {
        this.orderStatusId = paymentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
