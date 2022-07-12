package com.benedsmith.DigitalWallet.entity;

import com.benedsmith.DigitalWallet.enums.TransactionType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Builder
@Table(name = "transactions")
@NoArgsConstructor
public class Transaction {

    @Id
    private String transactionId;

    private String walletId;

    private TransactionType type;

    private BigDecimal amount;

    private LocalDateTime createdDate;

}