package com.benedsmith.DigitalWallet.service;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.enums.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    String newTransaction(String walletId, TransactionType transactionType, BigDecimal amount);

    List<Transaction> getTransactionHistory(String walletId);
}
