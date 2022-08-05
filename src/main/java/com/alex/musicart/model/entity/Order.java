package com.alex.musicart.model.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order extends CustomEntity {

    private long orderId;
    private BigDecimal price;
    private short statusId;
    private String status;
    private long userId;
    private User user;
    private String address;
    private Timestamp timestamp;
    private short paymentTypeId;
    private String paymentType;
    private List<Item> items = new ArrayList<>();

    public Order() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public short getStatusId() {
        return statusId;
    }

    public void setStatusId(short statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public short getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(short paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (orderId != order.orderId) return false;
        if (statusId != order.statusId) return false;
        if (userId != order.userId) return false;
        if (paymentTypeId != order.paymentTypeId) return false;
        if (!price.equals(order.price)) return false;
        if (!status.equals(order.status)) return false;
        if (!user.equals(order.user)) return false;
        if (!address.equals(order.address)) return false;
        if (!timestamp.equals(order.timestamp)) return false;
        if (!paymentType.equals(order.paymentType)) return false;
        return items.equals(order.items);
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + price.hashCode();
        result = 31 * result + (int) statusId;
        result = 31 * result + status.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + user.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + (int) paymentTypeId;
        result = 31 * result + paymentType.hashCode();
        result = 31 * result + items.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", price=").append(price);
        sb.append(", statusId=").append(statusId);
        sb.append(", status='").append(status).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", user=").append(user);
        sb.append(", address='").append(address).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", paymentTypeId=").append(paymentTypeId);
        sb.append(", paymentType='").append(paymentType).append('\'');
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
