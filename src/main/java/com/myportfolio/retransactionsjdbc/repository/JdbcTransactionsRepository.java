package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.House;
import com.myportfolio.retransactionsjdbc.model.Person;
import com.myportfolio.retransactionsjdbc.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcTransactionsRepository implements TransactionsRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    HouseRepository houseRepository;

    @Override
    public int saveTransaction(Transactions transactions) {
        try {
            String sql = "INSERT INTO Transactions(house_id, buyer_id, seller_id, transaction_date, transaction_amount) VALUES(?,?,?,?,?)";

            if (transactions.getHouse_id() <= 0 || transactions.getBuyer_id() <= 0 || transactions.getSeller_id() <= 0 || transactions.getTransaction_amount().compareTo(BigDecimal.ZERO) <= 0){
                throw new IllegalArgumentException("House id, buyer id, seller id, and amount cannot be 0 or a negative number.");
            }

            if (transactions.getTransaction_date() == null || transactions.getTransaction_date().toString().isEmpty()){
                throw new IllegalArgumentException("Transaction Date cannot be empty.");
            }

            return jdbcTemplate.update(sql, transactions.getHouse_id(), transactions.getBuyer_id(), transactions.getSeller_id(), transactions.getTransaction_date(), transactions.getTransaction_amount());
        }catch (DataAccessException dae){
            throw dae;
        }
    }

    @Override
    public List<Transactions> findAllTransactions() {
        try {
            String sql = "SELECT * FROM Transactions";

            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Transactions.class));
        }catch (DataAccessException dae){
            return Collections.emptyList();
        }
    }

    @Override
    public Transactions findTransactionById(int transactionID) {
        try {
            String sql = "SELECT * FROM Transactions WHERE transaction_id=?";

            if (transactionID <= 0) {
                throw new IllegalArgumentException("Transaction id cannot be 0 or a negative number.");
            }
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Transactions.class), transactionID);
        }catch (DataAccessException dae){
            throw dae;
        }
    }

    @Override
    public List<Transactions> findTransactionByHouseId(int houseID) {
        try {
            String sql = "SELECT * FROM Transactions WHERE house_id=?";

            if (houseID <= 0) {
                throw new IllegalArgumentException("House id cannot be 0 or a negative number.");
            }

            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Transactions.class), houseID);
        }catch (DataAccessException dae){
            return Collections.emptyList();
        }
    }

    @Override
    public List<Transactions> findTransactionByBuyerId(int buyerID) {
        try {
            String sql = "SELECT * FROM Transactions WHERE buyer_id=?";

            if (buyerID <= 0) {
                throw new IllegalArgumentException("Buyer id cannot be 0 or a negative number.");
            }

            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Transactions.class), buyerID);
        }catch (DataAccessException dae){
            return Collections.emptyList();
        }
    }

    @Override
    public List<Transactions> findTransactionBySellerId(int sellerID) {
        try {
            String sql = "SELECT * FROM Transactions WHERE seller_id=?";

            if (sellerID <= 0) {
                throw new IllegalArgumentException("Seller id cannot be 0 or a negative number.");
            }

            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Transactions.class), sellerID);
        }catch (DataAccessException dae){
            return Collections.emptyList();
        }
    }

    @Override
    public int deleteTransactionById(int transactionID) {
        try {
            String sql = "DELETE FROM Transactions WHERE transaction_id=?";

            return jdbcTemplate.update(sql, transactionID);
        }catch (DataAccessException dae){
            return 0;
        }
    }

    public void transferHouseOwnership(Transactions transactions, Person buyer, Person seller, House house){
        if (!"BUYER".equals(buyer.getRole()) || !"SELLER".equals(seller.getRole())){
            throw new IllegalArgumentException("A buyer must have 'BUYER' role and a seller 'SELLER' role");
        }
        if (buyer.getPerson_account_number() == null || seller.getPerson_account_number() == null){
            throw new IllegalArgumentException("Buyer and Seller must have an account number.");
        }
        if (seller.getPerson_id() != house.getSeller_id()){
            throw new IllegalArgumentException("Person id " + seller.getPerson_id() + " doesn't own the house located at " + house.getHouse_address() + ". Transaction failed.");
        }

        transactions.transactionRecord(buyer, seller, house);

        buyer.setRole("SELLER");
        seller.setRole("BUYER");
        transactions.setTransaction_date(LocalDate.now());
        transactions.setTransaction_amount(house.getHouse_price());

        personRepository.updatePerson(buyer);
        personRepository.updatePerson(seller);
        house.setSeller_id(buyer.getPerson_id());
        houseRepository.updateHouse(house);
    }
}
