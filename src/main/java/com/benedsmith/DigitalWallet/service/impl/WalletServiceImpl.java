package com.benedsmith.DigitalWallet.service.impl;

import com.benedsmith.DigitalWallet.entity.Transaction;
import com.benedsmith.DigitalWallet.entity.Wallet;
import com.benedsmith.DigitalWallet.enums.TransactionType;
import com.benedsmith.DigitalWallet.models.WalletRequestBody;
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
                .balance(walletRequestBody.getBalance())
                .maxDeposit(walletRequestBody.getMaxDeposit())
                .maxWithdraw(walletRequestBody.getMaxWithdraw())
                .minTransfer(walletRequestBody.getMinTransfer())
                .build();

        walletRepository.saveAndFlush(wallet);
        return wallet;
    }

    public boolean updateBalance(String walletId, BigDecimal amount, TransactionType transactionType) {
        // TODO in future: add meaningful responses here if conditions aren't met
        if (walletRepository.existsById(walletId)) {
            Optional<Wallet> wallet = walletRepository.findById(walletId);

            if (wallet.isPresent()) {
                Wallet walletObj = wallet.get();

                if (isTransactionAllowed(walletObj, amount, transactionType)) {
                    switch (transactionType) {
                        case CREDIT:
                            walletObj.setBalance(walletObj.getBalance().add(amount));
                            return true;
                        case DEBIT:
                            walletObj.setBalance(walletObj.getBalance().subtract(amount));
                            return true;
                    }
                } return false;

            } return false;

        } return false;
    }

    public boolean isTransactionAllowed(Wallet wallet, BigDecimal amount, TransactionType transactionType) {
        if (amount.compareTo(wallet.getMinTransfer()) < 0) {
            // transaction too small
            return false;
        }

        switch (transactionType) {
            case DEBIT:
                if (amount.compareTo(wallet.getMaxWithdraw()) > 0) {
                    // transaction too large
                    return false;
                }

                if (amount.compareTo(wallet.getBalance()) > 0) {
                    // wallet would be overdrawn
                    return false;
                }

                // otherwise, transaction is valid
                return true;

            case CREDIT:

                if (amount.compareTo(wallet.getMaxDeposit()) > 0) {
                    return false;
                }
                return true;
        }

        // case doesn't match
        return false;
    }

}
