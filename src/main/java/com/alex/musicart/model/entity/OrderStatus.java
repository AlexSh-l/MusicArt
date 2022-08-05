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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        if (orderStatusId != that.orderStatusId) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = orderStatusId;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderStatus{");
        sb.append("orderStatusId=").append(orderStatusId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
