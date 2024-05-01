package com.myportfolio.retransactionsjdbc.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transactions {

    private int transactionID;
    private int houseID;
    private int buyerID;
    private int sellerID;
    private Date transactionDate;
    private BigDecimal transactionAmount;

    public Transactions() {
    }

    public Transactions(int transactionID, int houseID, int buyerID, int sellerID, Date transactionDate, BigDecimal transactionAmount) {
        this.transactionID = transactionID;
        this.houseID = houseID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public int getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionID=" + transactionID +
                ", houseID=" + houseID +
                ", buyerID=" + buyerID +
                ", sellerID=" + sellerID +
                ", transactionDate=" + transactionDate +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
