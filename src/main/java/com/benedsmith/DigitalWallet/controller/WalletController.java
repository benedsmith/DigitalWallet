package com.benedsmith.DigitalWallet.controller;

import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.models.WalletRequestBody;
import com.benedsmith.DigitalWallet.service.WalletService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets/new")
    Wallet newWallet(@RequestBody WalletRequestBody walletRequestBody) {
        return walletService.newWallet(walletRequestBody);
    }
}
