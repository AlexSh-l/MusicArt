package com.alex.musicart.model.entity;

public class PaymentType extends CustomEntity {
    private short paymentTypeId;
    private String name;

    public PaymentType() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentType that = (PaymentType) o;
        if (paymentTypeId != that.paymentTypeId) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = paymentTypeId;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentType{");
        sb.append("paymentTypeId=").append(paymentTypeId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
