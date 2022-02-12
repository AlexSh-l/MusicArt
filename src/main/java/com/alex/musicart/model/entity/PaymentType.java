package com.alex.musicart.model.entity;

public class PaymentType extends CustomEntity {
    private short paymentTypeId;
    private String name;

    public PaymentType(){
    }

    public short getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(short paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
