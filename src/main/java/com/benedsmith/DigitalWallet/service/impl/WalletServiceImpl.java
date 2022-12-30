package com.benedsmith.DigitalWallet.service.impl;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.models.WalletRequestBody;
import com.benedsmith.DigitalWallet.repository.TransactionRepository;
import com.benedsmith.DigitalWallet.repository.WalletRepository;
import com.benedsmith.DigitalWallet.service.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.benedsmith.DigitalWallet.enums.TransactionType.CREDIT;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet newWallet(WalletRequestBody walletRequestBody) {
        Wallet wallet = Wallet.builder()
                .walletId(UUID.randomUUID().toString())
                .maxDeposit(walletRequestBody.getMaxDeposit())
                .maxWithdraw(walletRequestBody.getMaxWithdraw())
                .minTransfer(walletRequestBody.getMinTransfer())
                .build();

        walletRepository.saveAndFlush(wallet);
        return wallet;
    }
}
