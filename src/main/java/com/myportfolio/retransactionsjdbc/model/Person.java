package com.myportfolio.retransactionsjdbc.model;

import java.math.BigDecimal;

public class Person {

    private int person_id;
    private String person_name;
    private String person_account_number;
    private String person_contact_info;
    private String role;
    private BigDecimal balance;

    public Person() {
    }

    public Person(String person_name, String person_account_number, String person_contact_info, String role, BigDecimal balance) {
        this.person_name = person_name;
        this.person_account_number = person_account_number;
        this.person_contact_info = person_contact_info;
        this.role = role;
        this.balance = balance;
    }

    public Person(int person_id, String person_name, String person_account_number, String person_contact_info, String role, BigDecimal balance) {
        this.person_id = person_id;
        this.person_name = person_name;
        this.person_account_number = person_account_number;
        this.person_contact_info = person_contact_info;
        this.role = role;
        this.balance = balance;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_account_number() {
        return person_account_number;
    }

    public void setPerson_account_number(String person_account_number) {
        this.person_account_number = person_account_number;
    }

    public String getPerson_contact_info() {
        return person_contact_info;
    }

    public void setPerson_contact_info(String person_contact_info) {
        this.person_contact_info = person_contact_info;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", person_account_number='" + person_account_number + '\'' +
                ", person_contact_info='" + person_contact_info + '\'' +
                ", role='" + role + '\'' +
                ", balance=" + balance +
                '}';
    }
}
