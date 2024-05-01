package com.myportfolio.retransactionsjdbc.model;

public class Person {
    private int personID;
    private String personName;
    private String personAccountNumber;
    private String personContactInfo;
    private String role;

    public Person() {
    }

    public Person(int personID, String personName, String personAccountNumber, String personContactInfo, String role) {
        this.personID = personID;
        this.personName = personName;
        this.personAccountNumber = personAccountNumber;
        this.personContactInfo = personContactInfo;
        this.role = role;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonAccountNumber() {
        return personAccountNumber;
    }

    public void setPersonAccountNumber(String personAccountNumber) {
        this.personAccountNumber = personAccountNumber;
    }

    public String getPersonContactInfo() {
        return personContactInfo;
    }

    public void setPersonContactInfo(String personContactInfo) {
        this.personContactInfo = personContactInfo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", personName='" + personName + '\'' +
                ", personAccountNumber='" + personAccountNumber + '\'' +
                ", personContactInfo='" + personContactInfo + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
