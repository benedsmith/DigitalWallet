package com.benedsmith.DigitalWallet.service.impl;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.service.BalanceService;

import java.math.BigDecimal;
import java.util.List;

public class BalanceServiceImpl implements BalanceService {


    private final TransactionRepository transactionRepository;

    public BalanceServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;

    }

    @Override
    public BigDecimal calculateBalance(String walletId) {
        List<Transaction> transactionList = transactionRepository.findAllByWalletId(walletId);

        BigDecimal creditAmount = transactionList.stream()
                .filter(transaction -> transaction.getType().equals(TransactionType.CREDIT))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal debitAmount = transactionList.stream()
                .filter(transaction -> transaction.getType().equals(TransactionType.DEBIT))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return creditAmount.subtract(debitAmount);
    }
}
