package com.myportfolio.retransactionsjdbc.model;

import java.math.BigDecimal;

public class House {

    private int houseID;
    private String houseAddress;
    private BigDecimal housePrice;
    private int sellerID;

    public House() {
    }

    public House(int houseID, String houseAddress, BigDecimal housePrice, int sellerID) {
        this.houseID = houseID;
        this.houseAddress = houseAddress;
        this.housePrice = housePrice;
        this.sellerID = sellerID;
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public BigDecimal getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(BigDecimal housePrice) {
        this.housePrice = housePrice;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseID=" + houseID +
                ", houseAddress='" + houseAddress + '\'' +
                ", housePrice=" + housePrice +
                ", sellerID=" + sellerID +
                '}';
    }
}
