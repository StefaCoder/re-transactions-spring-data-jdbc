package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.House;
import com.myportfolio.retransactionsjdbc.model.Person;
import com.myportfolio.retransactionsjdbc.model.Transactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcTransactionsRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcTransactionsRepository jdbcTransactionsRepository;


    // --- test saveTransaction method ---

    @Test
    void validDataSaveTransaction(){
        Transactions transactions = new Transactions(6, 17, 18, LocalDate.of(2024, 05, 16), BigDecimal.valueOf(720000.00));

        int rowsAdded = jdbcTransactionsRepository.saveTransaction(transactions);

        assertEquals(1, rowsAdded);
        assertEquals(17, transactions.getBuyer_id());
        assertEquals(18, transactions.getSeller_id());
    }

    @Test
    void houseIdValueZeroThrowsIllegalArgumentException(){
        Transactions transactions = new Transactions(0, 17, 18, LocalDate.of(2024, 05, 16), BigDecimal.valueOf(720000.00));

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.saveTransaction(transactions);
        });
    }

    @Test
    void buyerIdValueZeroThrowsIllegalArgumentException(){
        Transactions transactions = new Transactions(6, 0, 18, LocalDate.of(2024, 05, 16), BigDecimal.valueOf(720000.00));

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.saveTransaction(transactions);
        });
    }

    @Test
    void sellerIdValueZeroThrowsIllegalArgumentException(){
        Transactions transactions = new Transactions(6, 17, 0, LocalDate.of(2024, 05, 16), BigDecimal.valueOf(720000.00));

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.saveTransaction(transactions);
        });
    }

    @Test
    void amountValueZeroThrowsIllegalArgumentException(){
        Transactions transactions = new Transactions(6, 17, 18, LocalDate.of(2024, 05, 16), BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.saveTransaction(transactions);
        });
    }

    @Test
    void transactionDateValueNullThrowsIllegalArgumentException(){
        Transactions transactions = new Transactions(6, 17, 18, null, BigDecimal.valueOf(720000.00));

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.saveTransaction(transactions);
        });
    }

    @Test
    void invalidSqlQueryThrowsDataAccessException(){
        String invalidSql = "INSERT INTO TransactionsS(house_id, buyer_id, seller_id, transaction_date, transaction_amount) VALUES(?,?,?,?,?)";
        Transactions transactions = new Transactions(6, 17, 18, LocalDate.of(2024, 05, 16), BigDecimal.valueOf(720000.00));

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.update(invalidSql, transactions.getBuyer_id(), transactions.getSeller_id(), transactions.getTransaction_date(), transactions.getTransaction_amount());
        });
    }

    // --- test findAllTransactions method ---

    @Test
    void invalidFindAllSqlQueryThrowsDataAccessException(){
        String invalidSql = "SELECT * FROM TransactionsS";

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.query(invalidSql, BeanPropertyRowMapper.newInstance(Transactions.class));
        });
    }

    // --- test findTransactionById method ---

    @Test
    void invalidFindByIdSqlQueryThrowsDataAccessException(){
        String invalidSql = "SELECT * FROM TransactionsS WHERE transaction_id=?";
        int transactionID = 100;

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.queryForObject(invalidSql, BeanPropertyRowMapper.newInstance(Transactions.class), transactionID);
        });
    }

    @Test
    void transactionIdZeroValueThrowsIllegalArgumentException(){
        int transactionID = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionById(transactionID);
        });
    }

    @Test
    void transactionIdNegativeValueThrowsIllegalArgumentException(){
        int transactionID = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionById(transactionID);
        });
    }

    // --- test findTransactionByHouseId method ---

    @Test
    void invalidFindByHouseIdSqlQueryThrowsDataAccessException(){
        String invalidSql = "SELECT * FROM TransactionsS WHERE house_id=?";
        int houseID = -10000;

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.query(invalidSql, BeanPropertyRowMapper.newInstance(Transactions.class), houseID);
        });
    }

    @Test
    void houseIdZeroValueThrowsIllegalArgumentException(){
        int houseID = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionByHouseId(houseID);
        });
    }

    @Test
    void houseIdNegativeValueThrowsIllegalArgumentException(){
        int houseID = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionByHouseId(houseID);
        });
    }

    // --- test findTransactionByBuyerId method ---

    @Test
    void invalidFindByBuyerIdSqlQueryThrowsDataAccessException(){
        String invalidSql = "SELECT * FROM TransactionsS WHERE buyer_id=?";
        int buyerID = 10000;

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.query(invalidSql, BeanPropertyRowMapper.newInstance(Transactions.class), buyerID);
        });
    }

    @Test
    void buyerIdZeroValueThrowsIllegalArgumentException(){
        int buyerID = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionByBuyerId(buyerID);
        });
    }

    @Test
    void buyerIdNegativeValueThrowsIllegalArgumentException(){
        int buyerID = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionByBuyerId(buyerID);
        });
    }

    // --- test findTransactionBySellerId method ---

    @Test
    void invalidFindBySellerIdSqlQueryThrowsDataAccessException(){
        String invalidSql = "SELECT * FROM TransactionsS WHERE seller_id=?";
        int sellerID = 10000;

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.query(invalidSql, BeanPropertyRowMapper.newInstance(Transactions.class), sellerID);
        });
    }

    @Test
    void sellerIdZeroValueThrowsIllegalArgumentException(){
        int sellerID = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionBySellerId(sellerID);
        });
    }

    @Test
    void sellerIdNegativeValueThrowsIllegalArgumentException(){
        int sellerID = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.findTransactionBySellerId(sellerID);
        });
    }

    // --- test deleteTransactionById method ---

    @Test
    void invalidDeleteByIdSqlQueryThrowsDataAccessException(){
        String invalidSql = "DELETE FROM TransactionsS WHERE transaction_id=?";
        int transactionID = 10000;

        assertThrows(DataAccessException.class, () -> {
            jdbcTemplate.update(invalidSql, transactionID);
        });
    }

    @Test
    void nonExistentIdReturnsZero(){
        int transactionID = -1000000;

        int rowsDeleted = jdbcTransactionsRepository.deleteTransactionById(transactionID);

        assertEquals(0, rowsDeleted);
    }

    // --- test transferHouseOwnership method ---

    @Test
    void noBuyerRoleInBuyerThrowsIllegalArgumentException(){
        Person p1 = new Person("testName1, testLastName1", "888999", "999-999-9999", "ADMIN", BigDecimal.TEN);
        Person p2 = new Person("testName2, testLastName2", "999888", "888-999-8888", "SELLER", BigDecimal.TEN);
        Transactions t1 = new Transactions(50, 100, 101, LocalDate.of(2024, 05, 17), BigDecimal.TEN);
        House h1 = new House(30, "111 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 101);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.transferHouseOwnership(t1, p1, p2, h1);
        });
    }

    @Test
    void noSellerRoleInSellerThrowsIllegalArgumentException() {
        Person p1 = new Person("testName1, testLastName1", "888999", "999-999-9999", "BUYER", BigDecimal.TEN);
        Person p2 = new Person("testName2, testLastName2", "999888", "888-999-8888", "ADMIN", BigDecimal.TEN);
        Transactions t1 = new Transactions(50, 100, 101, LocalDate.of(2024, 05, 17), BigDecimal.TEN);
        House h1 = new House(30, "111 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 101);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.transferHouseOwnership(t1, p1, p2, h1);
        });
    }

    @Test
    void buyerAccountNumberNullThrowsIllegalArgumentException(){
        Person p1 = new Person("testName1, testLastName1", "", "999-999-9999", "BUYER", BigDecimal.TEN);
        Person p2 = new Person("testName2, testLastName2", "999888", "888-999-8888", "SELLER", BigDecimal.TEN);
        Transactions t1 = new Transactions(50, 100, 101, LocalDate.of(2024, 05, 17), BigDecimal.TEN);
        House h1 = new House(30, "111 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 101);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.transferHouseOwnership(t1, p1, p2, h1);
        });
    }

    @Test
    void sellerAccountNumberNullThrowsIllegalArgumentException(){
        Person p1 = new Person("testName1, testLastName1", "888999", "999-999-9999", "BUYER", BigDecimal.TEN);
        Person p2 = new Person("testName2, testLastName2", "", "888-999-8888", "SELLER", BigDecimal.TEN);
        Transactions t1 = new Transactions(50, 100, 101, LocalDate.of(2024, 05, 17), BigDecimal.TEN);
        House h1 = new House(30, "111 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 101);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.transferHouseOwnership(t1, p1, p2, h1);
        });
    }

    @Test
    void sellerPersonIdNoMatchWithHouseSellerIdThrowsIllegalArgumentException(){
        Person p1 = new Person(200, "testName1, testLastName1", "888999", "999-999-9999", "BUYER", BigDecimal.TEN);
        Person p2 = new Person(300, "testName2, testLastName2", "999888", "888-999-8888", "SELLER", BigDecimal.TEN);
        Transactions t1 = new Transactions(50, 100, 101, LocalDate.of(2024, 05, 17), BigDecimal.TEN);
        House h1 = new House(30, "111 Test Street, New York, NY 12345 USA", BigDecimal.TEN, 101);

        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransactionsRepository.transferHouseOwnership(t1, p1, p2, h1);
        });
    }
}