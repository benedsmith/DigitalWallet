package com.benedsmith.DigitalWallet.service;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.repository.WalletRepository;
import com.benedsmith.DigitalWallet.service.impl.TransactionServiceImpl;
import com.benedsmith.DigitalWallet.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

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

    @Mock
    WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        walletService = Mockito.mock(WalletServiceImpl.class);
        walletRepository = Mockito.mock(WalletRepository.class);

        transactionService = new TransactionServiceImpl(transactionRepository, walletRepository);
    }

    @Test
    public void newTransactionSuccessTest() {

        Wallet wallet = Wallet.builder()
                .walletId("456")
                .maxDeposit(BigDecimal.valueOf(999))
                .maxWithdraw(BigDecimal.valueOf(999))
                .minTransfer(BigDecimal.valueOf(1))
                .build();

        when(walletRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(wallet));

        Transaction expectedTransaction = Transaction.builder()
                .amount(BigDecimal.TEN)
                .walletId("walletId")
                .type(TransactionType.CREDIT)
                .build();

        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        Transaction transactionResponse = transactionService.newTransaction("walletId", TransactionType.CREDIT, BigDecimal.TEN);

        Mockito.verify(transactionRepository, Mockito.times(1)).saveAndFlush(any(Transaction.class));
        Mockito.verify(transactionRepository).saveAndFlush(transactionArgumentCaptor.capture());

        Transaction capturedTransaction = transactionArgumentCaptor.getValue();

        assertEquals(expectedTransaction.getWalletId(), capturedTransaction.getWalletId());
        assertEquals(expectedTransaction.getAmount(), capturedTransaction.getAmount());
        assertEquals(expectedTransaction.getType(), capturedTransaction.getType());

    }
}
