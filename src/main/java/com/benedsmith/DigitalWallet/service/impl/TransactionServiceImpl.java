package com.benedsmith.DigitalWallet.service.impl;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.repository.WalletRepository;
import com.benedsmith.DigitalWallet.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final BalanceServiceImpl balanceService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.balanceService = new BalanceServiceImpl(transactionRepository);
    }

    public Transaction newTransaction(String walletId, TransactionType transactionType, BigDecimal amount) {
        Transaction newTransaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .amount(amount)
                .type(transactionType)
                .walletId(walletId)
                .createdDate(LocalDateTime.now())
                .build();


        Optional<Wallet> walletDestination = walletRepository.findById(walletId);

        if (walletDestination.isPresent()) {

        // TODO throw real exceptions here
            if (isTransactionAllowed(walletDestination.get(), newTransaction)) {

                // perform transaction
                try {
                    transactionRepository.saveAndFlush(newTransaction);
                    return newTransaction;

                } catch (Exception e) {
                    log.error(e.toString());
                    log.error("Transaction failed during creation");
                    return null;
                }
            }
            else {
                log.error("Transaction not permitted");
                return null;
            }
        } else {
            log.error("wallet not found, discarding transaction");
            return null;
        }
    }

    public boolean isTransactionAllowed(Wallet wallet, Transaction transaction) {
        if (transaction.getAmount().compareTo(wallet.getMinTransfer()) < 0) {
            // transaction too small
            return false;
        }

        // this syntax could be simplified, but readability is key here
        switch (transaction.getType()) {
            case DEBIT:
                if (transaction.getAmount().compareTo(wallet.getMaxWithdraw()) > 0) {
                    // transaction too large
                    return false;
                }

                if (transaction.getAmount().compareTo(balanceService.calculateBalance(wallet.getWalletId())) > 0) {
                    // wallet would be overdrawn
                    return false;
                }

                // otherwise, transaction is valid
                return true;

            case CREDIT:

                if (transaction.getAmount().compareTo(wallet.getMaxDeposit()) > 0) {
                    return false;
                }
                return true;
        }

        // case doesn't match
        return false;
    }


    public Transaction getTransaction(String transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        return transaction.orElse(null);
    }


}
