package com.benedsmith.DigitalWallet.controller;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.models.TransactionRequestBody;
import com.benedsmith.DigitalWallet.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions/new")
    String newTransaction(@RequestBody TransactionRequestBody requestBody) {
        return transactionService.newTransaction(
                requestBody.getWalletId(),
                requestBody.getTransactionType(),
                requestBody.getAmount());
    }

    @GetMapping("/transactions/{walletId}")
    List<Transaction> getTransactionHistory(@PathVariable String walletId) {
        return transactionService.getTransactionHistory(walletId);
    }

}
