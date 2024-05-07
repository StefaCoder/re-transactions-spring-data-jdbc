package com.myportfolio.retransactionsjdbc.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transactions {

    private int transaction_id;
    private int house_id;
    private int buyer_id;
    private int seller_id;
    private LocalDate transaction_date;
    private BigDecimal transaction_amount;

    public Transactions() {
    }

    public Transactions(int transaction_id, int house_id, int buyer_id, int seller_id, LocalDate transaction_date, BigDecimal transaction_amount) {
        this.transaction_id = transaction_id;
        this.house_id = house_id;
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.transaction_date = transaction_date;
        this.transaction_amount = transaction_amount;
    }

    public Transactions(int house_id, int buyer_id, int seller_id, LocalDate transaction_date, BigDecimal transaction_amount) {
        this.house_id = house_id;
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.transaction_date = transaction_date;
        this.transaction_amount = transaction_amount;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public LocalDate getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDate transaction_date) {
        this.transaction_date = transaction_date;
    }

    public BigDecimal getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(BigDecimal transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transaction_id=" + transaction_id +
                ", house_id=" + house_id +
                ", buyer_id=" + buyer_id +
                ", seller_id=" + seller_id +
                ", transaction_date=" + transaction_date +
                ", transaction_amount=" + transaction_amount +
                '}';
    }
}
