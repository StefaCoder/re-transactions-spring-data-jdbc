package com.myportfolio.retransactionsjdbc.model;

import java.math.BigDecimal;

public class House {

    private int house_id;
    private String house_address;
    private BigDecimal house_price;
    private int seller_id;

    public House() {
    }

    public House(int house_id, String house_address, BigDecimal house_price, int seller_id) {
        this.house_id = house_id;
        this.house_address = house_address;
        this.house_price = house_price;
        this.seller_id = seller_id;
    }

    public House(String house_address, BigDecimal house_price, int seller_id) {
        this.house_address = house_address;
        this.house_price = house_price;
        this.seller_id = seller_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getHouse_address() {
        return house_address;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public BigDecimal getHouse_price() {
        return house_price;
    }

    public void setHouse_price(BigDecimal house_price) {
        this.house_price = house_price;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    @Override
    public String toString() {
        return "House{" +
                "house_id=" + house_id +
                ", house_address='" + house_address + '\'' +
                ", house_price=" + house_price +
                ", seller_id=" + seller_id +
                '}';
    }
}
