package com.benedsmith.DigitalWallet.repository;

import com.benedsmith.DigitalWallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, String> {

    @Override
    Optional<Wallet> findById(String walletId);
}
