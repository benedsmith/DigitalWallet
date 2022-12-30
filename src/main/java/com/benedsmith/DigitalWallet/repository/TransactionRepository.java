package com.benedsmith.DigitalWallet.repository;

import com.benedsmith.DigitalWallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
//    @Query("select * from transactions where walletId='?1'")
    List<Transaction> findAllByWalletId(String walletId);

    @Override
    Optional<Transaction> findById(String transactionId);
}
