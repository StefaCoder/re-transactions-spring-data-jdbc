package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class JdbcPersonRepositoryTest {

    @Autowired
    JdbcPersonRepository jdbcPersonRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    // --- test savePerson method ---

    @Test
    void invalidRoleThrowsIllegalArgumentException(){
        Person person = new Person("Frannk Ruudds", "123456", "111-111-1111", "ADMIN", BigDecimal.TEN);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcPersonRepository.savePerson(person);
        });
    }

    @Test
    void blankAccountNumberThrowsIllegalArgumentException(){
        Person person = new Person("Frannk Ruudds", "", "111-111-1111", "BUYER", BigDecimal.TEN);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcPersonRepository.savePerson(person);
        });
    }

    @Test
    void nullBalanceThrowsIllegalArgumentException(){
        Person person = new Person("Frannk Ruudds", "123456", "111-111-1111", "BUYER", null);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcPersonRepository.savePerson(person);
        });
    }

    @Test
    void validDataInAllFieldsSavePerson(){
        Person person = new Person("Frannk Ruud", "123456", "111-111-1111", "SELLER", BigDecimal.TEN);

        int numOfRows = jdbcPersonRepository.savePerson(person);

        assertEquals(1, numOfRows);
        assertEquals("123456", person.getPerson_account_number());
        assertEquals("111-111-1111", person.getPerson_contact_info());
    }

    @Test
    void invalidSqlQueryThrowsDataAccessException(){
        String invalidSqlQuery = "INSERT INTO PersonN (person_name, person_account_number, person_contact_info, role, balance) VALUES(?,?,?,?,?)";
        Person person = new Person("Frannk Ruud", "123456", "111-111-1111", "SELLER", BigDecimal.TEN);

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.update(invalidSqlQuery, person.getPerson_name(), person.getPerson_account_number(), person.getPerson_contact_info(), person.getRole(), person.getBalance());
        });
    }

    // --- test updatePerson method ---

    @Test
    void invalidRoleUpdateThrowsIllegalArgumentException() {
        Person person = new Person("Frannk Ruud", "123456", "111-111-1111", "ADMIN", BigDecimal.TEN);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcPersonRepository.updatePerson(person);
        });
    }

    @Test
    void invalidUpdateSqlQueryThrowsDataAccessException(){
        String invalidUpdateSqlQuery = "UPDATE PersonN SET role=?, balance=? WHERE person_id=?";
        Person person = new Person(15, "Gregg Milleros", "325794", "222-222-2222", "BUYER", BigDecimal.valueOf(4000000.00));

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.update(invalidUpdateSqlQuery, person.getRole(), person.getBalance(), person.getPerson_id());
        });
    }

    @Test
    void validDataInAllFieldsUpdatePerson(){
        Person person = new Person(15, "Gregg Milleros", "325794", "222-222-2222", "BUYER", BigDecimal.valueOf(4000000.00));

        int numOfRows = jdbcPersonRepository.updatePerson(person);

        assertEquals(1, numOfRows);
    }

    // --- test findPersonById method ---

    @Test
    void invalidFindPersonByIdSqlQueryThrowsDataAccessException(){
        String invalidFindByIdSqlQuery = "SELECT * FROM PersonN WHERE person_id=?";
        Person person = new Person(15, "Gregg Milleros", "325794", "222-222-2222", "BUYER", BigDecimal.valueOf(4000000.00));

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.queryForObject(invalidFindByIdSqlQuery, BeanPropertyRowMapper.newInstance(Person.class), person.getPerson_id());
        });
    }

    @Test
    void invalidIdInFindPersonByIdThrowsDataAccessException(){
        String sqlQuery = "SELECT * FROM Person WHERE person_id=?";
        int nonExistentId = -15;

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.queryForObject(sqlQuery, BeanPropertyRowMapper.newInstance(Person.class), nonExistentId);
        });
    }

    @Test
    void existentPersonIdShouldReturnPerson(){
        int personID = 15;

        Person person = jdbcPersonRepository.findPersonById(personID);

        assertNotNull(person, "A person object is retrieved from the database.");
        assertEquals("325794", person.getPerson_account_number());
        assertEquals("BUYER", person.getRole());
    }

    // --- test findPersonByAccountNumber method ---

    @Test
    void invalidFindByAccNumberSqlQueryThrowsDataAccessException(){
        String invalidFindByAccNumSqlQuery = "SELECT * FROM PersonN WHERE person_account_number=?";
        Person person = new Person(15, "Gregg Milleros", "325794", "222-222-2222", "BUYER", BigDecimal.valueOf(4000000.00));

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.queryForObject(invalidFindByAccNumSqlQuery, BeanPropertyRowMapper.newInstance(Person.class), person.getPerson_account_number());
        });
    }

    @Test
    void invalidAccNumInFindByAccNumberThrowsDataAccessException(){
        String sqlQuery = "SELECT * FROM Person WHERE person_account_number=?";
        String nonExistentAccNum = "-999999";

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.queryForObject(sqlQuery, BeanPropertyRowMapper.newInstance(Person.class), nonExistentAccNum);
        });
    }

    @Test
    void existentAccNumReturnsPerson(){
        String accNum = "325794";

        Person person = jdbcPersonRepository.findPersonByAccountNumber(accNum);

        assertNotNull(person, "A person object is retrieved from the database.");
        assertEquals("Gregg Milleros", person.getPerson_name());
        assertEquals("222-222-2222", person.getPerson_contact_info());
    }

    // --- test findPersonByRole method ---

    @Test
    void invalidFindByRoleSqlQueryThrowsDataAccessException(){
        String invalidFindByRoleSqlQuery = "SELECT * FROM PersonN WHERE role=?";
        Person person = new Person(15, "Gregg Milleros", "325794", "222-222-2222", "BUYER", BigDecimal.valueOf(4000000.00));

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.query(invalidFindByRoleSqlQuery, BeanPropertyRowMapper.newInstance(Person.class), person.getRole());
        });
    }

    @Test
    void invalidRoleInFindByRoleReturnsEmptyList(){
        String sqlQuery = "SELECT * FROM Person WHERE role=?";
        String nonExistentRole = "ADMIN";

        List<Person> persons = jdbcTemplate.query(sqlQuery, BeanPropertyRowMapper.newInstance(Person.class), nonExistentRole);

        assertTrue(persons.isEmpty(), "List is empty because the role is not BUYER or SELLER.");
    }

    @Test
    void existentRoleReturnsListOfPersons(){
        String role = "BUYER";

        List<Person> persons = jdbcPersonRepository.findPersonByRole(role);

        assertFalse(persons.isEmpty(), "List should return all persons with BUYER role.");
    }

    // --- test deletePersonById method ---

    @Test
    void invalidDeleteByIdSqlQueryThrowsDataAccessException(){
        String invalidSqlQuery = "DELETE FROM PersonN WHERE person_id=?";
        int personID = 17;

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.update(invalidSqlQuery, personID);
        });
    }

    @Test
    void invalidIdInDeleteByIdReturnsZeroRowsDeleted(){
        String sql = "DELETE FROM Person WHERE person_id=?";
        int personID = -1000;

        int rowDeleted =  jdbcTemplate.update(sql, personID);

        assertEquals(0, rowDeleted, "Expected 0 rows to be deleted because the id is non-existent.");
    }

    // --- test deleteAll method ---

    @Test
    void invalidDeleteAllSqlQueryThrowsDataAccessException(){
        String invalidSqlQuery = "DELETE FROM PersonN";

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.update(invalidSqlQuery);
        });
    }
}