package com.benedsmith.DigitalWallet.service.impl;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletServiceImpl walletService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WalletServiceImpl walletService) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
    }

    public String newTransaction(String walletId, TransactionType transactionType, BigDecimal amount) {
        Transaction newTransaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .amount(amount)
                .type(transactionType)
                .walletId(walletId)
                .createdDate(LocalDateTime.now())
                .build();

        // perform transaction
        if (walletService.updateBalance(walletId, amount, transactionType)) {
            // record transaction
            transactionRepository.saveAndFlush(newTransaction);
            return "Successful transaction";
        }

        // Todo: meaningful error message/logging
        return "Transaction failed";
    }

    public List<Transaction> getTransactionHistory(String walletId) {
        return transactionRepository.findAllByWalletId(walletId);
    }
}
