package com.myportfolio.retransactionsjdbc.repository;

import com.myportfolio.retransactionsjdbc.model.Transactions;

import java.util.List;

public interface TransactionsRepository {

    int saveTransaction(Transactions transactions);

    Transactions findTransactionById(int transactionID);

    List<Transactions> findTransactionByHouseId(int houseID);

    List<Transactions> findTransactionByBuyerId(int buyerID);

    List<Transactions> findTransactionBySellerId(int sellerID);

    int deleteTransactionById(int transactionID);

}
