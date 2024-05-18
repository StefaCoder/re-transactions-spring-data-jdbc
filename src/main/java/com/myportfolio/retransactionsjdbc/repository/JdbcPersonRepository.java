package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcPersonRepository implements PersonRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int savePerson(Person person) throws DataAccessException{
        String sql = "INSERT INTO Person (person_name, person_account_number, person_contact_info, role, balance) VALUES(?,?,?,?,?)";
        String role = person.getRole();
        BigDecimal balance = person.getBalance();

        try {
            if (!"BUYER".equals(role) && !"SELLER".equals(role)){
                throw new IllegalArgumentException("Invalid role provided: " + role);
            }

            if ("".equals(person.getPerson_account_number())){
                throw new IllegalArgumentException("Invalid entry. Account Number cannot be blank.");
            }

            if (balance == null || balance.compareTo(BigDecimal.ZERO) <= 0 ){
                throw new IllegalArgumentException("Balance cannot be null, 0 or a negative number.");
            }

            return jdbcTemplate.update(sql, person.getPerson_name(), person.getPerson_account_number(), person.getPerson_contact_info(), person.getRole(), person.getBalance());
        }catch (DataAccessException dae){
            throw dae;
        }
    }

    @Override
    public int updatePerson(Person person) {
        try {
            String sql = "UPDATE Person SET role=?, balance=? WHERE person_id=?";
            String role = person.getRole();

            if (!"BUYER".equals(role) && !"SELLER".equals(role)){
                throw new IllegalArgumentException("Invalid role provided: " + role);
            }

            return jdbcTemplate.update(sql, role, person.getBalance(), person.getPerson_id());
        }catch (DataAccessException dae) {
            throw dae;
        }
    }

    @Override
    public Person findPersonById(int personID) {
        String sql = "SELECT * FROM Person WHERE person_id=?";

        try {
            Person personObj = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Person.class), personID);
            return personObj;
        }catch (DataAccessException dae){
            System.out.println("Person ID not found. " + dae.getMessage());
            return null;
        }
    }

    @Override
    public Person findPersonByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM Person WHERE person_account_number=?";
        try {
            Person personObj = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Person.class), accountNumber);
            return personObj;
        }catch(DataAccessException dae) {
            System.out.println("Account number not found. " + dae.getMessage());
            return null;
        }
    }

    @Override
    public List<Person> findPersonByRole(String role) {
        String sql = "SELECT * FROM Person WHERE role=?";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Person.class), role);
        }catch (DataAccessException dae) {
            return Collections.emptyList();
        }
    }

    @Override
    public int deletePersonById(int personID) {
        String sql = "DELETE FROM Person WHERE person_id=?";

        try {
            return jdbcTemplate.update(sql, personID);
        }catch (DataAccessException dae) {
            throw dae;
        }
    }

    @Override
    public int deleteAll() {
        String sql = "DELETE FROM Person";
        try {
            return jdbcTemplate.update(sql);
        }catch (DataAccessException dae){
            throw dae;
        }
    }
}
