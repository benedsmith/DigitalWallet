package com.benedsmith.DigitalWallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallets")
@Builder
public class Wallet {

    @Id
    private String walletId;

    @JsonIgnore
    @Column(name="max_deposit")
    private BigDecimal maxDeposit;

    @JsonIgnore
    @Column(name="min_transfer")
    private BigDecimal minTransfer;

    @JsonIgnore
    @Column(name="max_withdraw")
    private BigDecimal maxWithdraw;

    @Setter
    private BigDecimal balance;
}
