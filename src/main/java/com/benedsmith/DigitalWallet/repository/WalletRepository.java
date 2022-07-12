package com.benedsmith.DigitalWallet.repository;

import com.benedsmith.DigitalWallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}
