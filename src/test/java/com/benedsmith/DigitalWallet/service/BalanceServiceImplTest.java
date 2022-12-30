package com.benedsmith.DigitalWallet.service;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.service.impl.BalanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BalanceServiceImplTest {

    BalanceService balanceService;
    @Mock
    TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        this.transactionRepository = Mockito.mock(TransactionRepository.class);
        this.balanceService = new BalanceServiceImpl(transactionRepository);
    }

    @Test
    public void balanceServiceOneTransactionTest() {

        Transaction transaction1 = Transaction.builder()
                .transactionId("1")
                .amount(BigDecimal.ONE)
                .type(TransactionType.CREDIT)
                .build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);

        when(transactionRepository.findAllByWalletId(any(String.class))).thenReturn(transactionList);

        BigDecimal balance = balanceService.calculateBalance("mocked");

        assertEquals(BigDecimal.ONE, balance);
    }

    @Test
    public void balanceServicePositiveBalanceTest() {

        Transaction transaction1 = Transaction.builder()
                .transactionId("1")
                .amount(BigDecimal.TEN)
                .type(TransactionType.CREDIT)
                .build();

        Transaction transaction2 = Transaction.builder()
                .transactionId("2")
                .amount(BigDecimal.ONE)
                .type(TransactionType.DEBIT)
                .build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        when(transactionRepository.findAllByWalletId(any(String.class))).thenReturn(transactionList);

        BigDecimal balance = balanceService.calculateBalance("mocked");

        assertEquals(BigDecimal.valueOf(9), balance);
    }

    @Test
    public void balanceServiceNegativeBalanceTest() {

        Transaction transaction1 = Transaction.builder()
                .transactionId("1")
                .amount(BigDecimal.TEN)
                .type(TransactionType.DEBIT)
                .build();

        Transaction transaction2 = Transaction.builder()
                .transactionId("2")
                .amount(BigDecimal.ONE)
                .type(TransactionType.CREDIT)
                .build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        when(transactionRepository.findAllByWalletId(any(String.class))).thenReturn(transactionList);

        BigDecimal balance = balanceService.calculateBalance("mocked");

        assertEquals(BigDecimal.valueOf(-9), balance);
    }

}
