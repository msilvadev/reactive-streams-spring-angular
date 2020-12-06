package com.msilva.reactiveorders.domains;

import java.io.Serializable;
import java.util.Objects;

public class OrderDomain implements Serializable {

    private static final long serialVersionUID = -9017671870108598973L;

    private String symbol;
    private String side;
    private String quantity;
    private String price;
    private String instrument;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDomain that = (OrderDomain) o;
        return Objects.equals(side, that.side) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(price, that.price) &&
                Objects.equals(instrument, that.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, quantity, price, instrument);
    }

    @Override
    public String toString() {
        return "OrderDomain{" +
                "side='" + side + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", instrument='" + instrument + '\'' +
                '}';
    }
}
