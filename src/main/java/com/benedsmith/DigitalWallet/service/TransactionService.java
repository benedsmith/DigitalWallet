package com.benedsmith.DigitalWallet.service;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.enums.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction newTransaction(String walletId, TransactionType transactionType, BigDecimal amount);

    boolean isTransactionAllowed(Wallet wallet, Transaction transaction);

    Transaction getTransaction(String transactionId);

}
