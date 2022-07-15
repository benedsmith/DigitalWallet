package com.benedsmith.DigitalWallet.service;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.service.impl.TransactionServiceImpl;
import com.benedsmith.DigitalWallet.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {
    @Autowired
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    WalletServiceImpl walletService;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        walletService = Mockito.mock(WalletServiceImpl.class);

        transactionService = new TransactionServiceImpl(transactionRepository, walletService);
    }

    @Test
    public void newTransactionFailureTest() {
        when(walletService.updateBalance(any(), any(), any())).thenReturn(false);

        String walletId = "anything";
        TransactionType transactionType = TransactionType.CREDIT;
        BigDecimal amount = new BigDecimal(1);

        assertEquals("Transaction failed",
                transactionService.newTransaction(walletId, transactionType, amount));
    }

    @Test
    public void newTransactionSuccessTest() {
        when(walletService.updateBalance(any(), any(), any())).thenReturn(true);

        String walletId = "anything";
        TransactionType transactionType = TransactionType.CREDIT;
        BigDecimal amount = new BigDecimal(1);

        assertEquals("Successful transaction",
                transactionService.newTransaction(walletId, transactionType, amount));
    }


    @Test
    public void getTransactionHistorySuccessTest() {
        Transaction transaction1 = new Transaction("anyId", "anyWalletId", TransactionType.CREDIT, new BigDecimal(1), LocalDateTime.now());
        Transaction transaction2 = new Transaction("anyId", "anyWalletId", TransactionType.CREDIT, new BigDecimal(2), LocalDateTime.now());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findAllByWalletId("anyWalletId")).thenReturn(transactions);

        assertEquals(transactionService.getTransactionHistory("anyWalletId"), transactions);
    }
}
