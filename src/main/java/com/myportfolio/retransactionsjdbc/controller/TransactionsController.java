package com.myportfolio.retransactionsjdbc.controller;

import com.myportfolio.retransactionsjdbc.model.House;
import com.myportfolio.retransactionsjdbc.model.Person;
import com.myportfolio.retransactionsjdbc.model.Transactions;
import com.myportfolio.retransactionsjdbc.repository.HouseRepository;
import com.myportfolio.retransactionsjdbc.repository.JdbcTransactionsRepository;
import com.myportfolio.retransactionsjdbc.repository.PersonRepository;
import com.myportfolio.retransactionsjdbc.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    JdbcTransactionsRepository jdbcTransactionsRepository;

    @PostMapping("/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody Transactions transactions){
        try {
            transactionsRepository.saveTransaction(new Transactions(transactions.getHouse_id(), transactions.getBuyer_id(), transactions.getSeller_id(), transactions.getTransaction_date(), transactions.getTransaction_amount()));
            return new ResponseEntity<>("Transaction successfully created.", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Something went wrong while creating a transactions. " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/all")
    public ResponseEntity<List<Transactions>> getAllTransactions(){
        try {
            List<Transactions> allTransactions = transactionsRepository.findAllTransactions();
            if (allTransactions != null){
                System.out.println("Transactions retrieved successfully.");
                return new ResponseEntity<>(allTransactions, HttpStatus.OK);
            }else {
                System.out.println("Cannot find transactions.");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            System.out.println("Something went wrong while retrieving transactions. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transactions> getTransactionById(@PathVariable("id") int transactionID){
        try {
            Transactions transactionsObj = transactionsRepository.findTransactionById(transactionID);
            if (transactionsObj != null){
                return new ResponseEntity<>(transactionsObj, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            System.out.println("Something went wrong while retrieving transactions by id. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/house/{id}")
    public ResponseEntity<List<Transactions>> getTransactionsByHouseId(@PathVariable("id") int houseID){
        try {
            List<Transactions> transactions = transactionsRepository.findTransactionByHouseId(houseID);
            if (transactions.isEmpty()) {
                System.out.println("Cannot find transactions with house id " + houseID + ".");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                System.out.println("Transactions with house id " + houseID + " successfully retrieved.");
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            }
        }catch (Exception e){
            System.out.println("Something went wrong while retrieving transactions by house id. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/buyer/{id}")
    public ResponseEntity<List<Transactions>> getTransactionsByBuyerId(@PathVariable("id") int buyerID){
        try {
            List<Transactions> transactions = transactionsRepository.findTransactionByBuyerId(buyerID);
            if (transactions.isEmpty()) {
                System.out.println("Cannot find transactions with buyer id " + buyerID + ".");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                System.out.println("Transactions with buyer id " + buyerID + " successfully retrieved.");
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            }
        }catch (Exception e){
            System.out.println("Something went wrong while retrieving transactions by buyer id. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transactions/seller/{id}")
    public ResponseEntity<List<Transactions>> getTransactionsBySellerId(@PathVariable("id") int sellerID){
        try {
            List<Transactions> transactions = transactionsRepository.findTransactionBySellerId(sellerID);
            if (transactions.isEmpty()) {
                System.out.println("Cannot find transactions with seller id " + sellerID + ".");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                System.out.println("Transactions with seller id " + sellerID + " successfully retrieved.");
                return new ResponseEntity<>(transactions, HttpStatus.OK);
            }
        }catch (Exception e){
            System.out.println("Something went wrong while retrieving transactions by seller id. " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> removeTransactionById(@PathVariable("id") int transactionID){
        try {
            int response = transactionsRepository.deleteTransactionById(transactionID);
            if (response == 0){
                return new ResponseEntity<>("Cannot find transaction id " + transactionID + ".", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>("Transaction with id " + transactionID + " successfully deleted.", HttpStatus.OK);
            }
        }catch (Exception e){
            System.out.println("Something went wrong while deleting transactions by id." + e.getMessage());
            return new ResponseEntity<>("Error while deleting transaction.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/transactions/ownership")
    public ResponseEntity<String> createTransferOwnership(@RequestBody Transactions transactions){
        try {
            Person buyer = personRepository.findPersonById(transactions.getBuyer_id());
            Person seller = personRepository.findPersonById(transactions.getSeller_id());
            House house = houseRepository.findHouseById(transactions.getHouse_id());

            jdbcTransactionsRepository.transferHouseOwnership(transactions, buyer, seller, house);
            transactionsRepository.saveTransaction(transactions);
            return new ResponseEntity<>("Ownership successfully transferred.", HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Something went wrong while transferring ownership. " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
