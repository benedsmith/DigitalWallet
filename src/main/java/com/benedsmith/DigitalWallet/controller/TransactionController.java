package com.benedsmith.DigitalWallet.controller;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.models.TransactionRequestBody;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/transactions/")
    public ResponseEntity<Transaction> newTransaction(@RequestBody TransactionRequestBody requestBody) {
        Transaction createdTransaction = transactionService.newTransaction(
                requestBody.getWalletId(),
                requestBody.getTransactionType(),
                requestBody.getAmount());

        if (createdTransaction == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdTransaction.getTransactionId())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdTransaction);
        }
    }

    @GetMapping("/transactions/{transactionId}")
    ResponseEntity<Transaction> getTransaction(@PathVariable String transactionId) {
        Transaction foundTransaction = transactionService.getTransaction(transactionId);
        if (foundTransaction == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundTransaction);
        }
    }


    @GetMapping("/transactions/all/{walletId}/")
    ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String walletId) {
        List<Transaction> foundTransactions = transactionRepository.findAllByWalletId(walletId);
        if (foundTransactions == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundTransactions);
        }
    }

}
