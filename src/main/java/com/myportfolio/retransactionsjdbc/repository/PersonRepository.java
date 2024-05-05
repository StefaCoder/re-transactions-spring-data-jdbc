package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.Person;

import java.util.List;

public interface PersonRepository {

    int savePerson(Person person);

    int updatePerson(Person person);

    Person findPersonById(int personID);

    Person findPersonByAccountNumber(String accountNumber);

    List<Person> findPersonByRole(String role);

    int deletePersonById(int personID);

    int deleteAll();
}
