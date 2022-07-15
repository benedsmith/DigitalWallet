package com.benedsmith.DigitalWallet.service;

import com.benedsmith.DigitalWallet.controller.TransactionController;
import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.models.TransactionRequestBody;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.service.impl.TransactionServiceImpl;
import com.benedsmith.DigitalWallet.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class IntegrationTests {

    @Mock
    TransactionRepository transactionRepository;

    TransactionController transactionController;

    TransactionService transactionService;

    @Mock
    WalletServiceImpl walletService;

    @BeforeEach
    void setUp() {
        this.transactionRepository = Mockito.mock(TransactionRepository.class);
        this.walletService = Mockito.mock(WalletServiceImpl.class);

        this.transactionService = new TransactionServiceImpl(transactionRepository, walletService);
        this.transactionController = new TransactionController(transactionService);
    }


    @Test
    public void getNewTransactionIntegrationTest() {

        Transaction transactionExpected = new Transaction();
        transactionExpected.setTransactionId("123");
        transactionExpected.setType(TransactionType.CREDIT);
        transactionExpected.setWalletId("456");
        transactionExpected.setAmount(new BigDecimal(1));
        transactionExpected.setCreatedDate(LocalDateTime.now());

        Mockito.when(walletService.updateBalance(any(), any(), any())).thenReturn(true);

        TransactionRequestBody requestBody = new TransactionRequestBody("456", TransactionType.CREDIT, new BigDecimal(1));

        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        transactionController.newTransaction(requestBody);

        Mockito.verify(transactionRepository, Mockito.times(1)).saveAndFlush(any(Transaction.class));
        Mockito.verify(transactionRepository).saveAndFlush(transactionArgumentCaptor.capture());

        Transaction capturedTransaction = transactionArgumentCaptor.getValue();

        assertEquals(transactionExpected.getWalletId(), capturedTransaction.getWalletId());
        assertEquals(transactionExpected.getAmount(), capturedTransaction.getAmount());
        assertEquals(transactionExpected.getType(), capturedTransaction.getType());

        // These are independent of what the requestBody contains, check types
        assertEquals(String.class, capturedTransaction.getTransactionId().getClass());
        assertEquals(LocalDateTime.class, capturedTransaction.getCreatedDate().getClass());

    }


}
