package com.benedsmith.DigitalWallet.controller;

import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.models.WalletRequestBody;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.service.BalanceService;
import com.benedsmith.DigitalWallet.service.WalletService;
import com.benedsmith.DigitalWallet.service.impl.BalanceServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {

    private final WalletService walletService;

    private final BalanceService balanceService;

    public WalletController(WalletService walletService, TransactionRepository transactionRepository) {
        this.walletService = walletService;
        this.balanceService = new BalanceServiceImpl(transactionRepository);
    }

    @PostMapping("/wallets/")
    ResponseEntity<Wallet> newWallet(@RequestBody WalletRequestBody walletRequestBody) {
        // TODO add proper response here
        return ResponseEntity.ok(walletService.newWallet(walletRequestBody));
    }

    @GetMapping("/wallets/{walletId}/balance")
    ResponseEntity<BigDecimal> getWalletBalance(@RequestParam String walletId) {
        // TODO add proper response here
        return ResponseEntity.ok(balanceService.calculateBalance(walletId));
    }

}
