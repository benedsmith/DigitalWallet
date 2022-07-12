package com.benedsmith.DigitalWallet.service;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.models.WalletRequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    Wallet newWallet(WalletRequestBody walletRequestBody);
}

