package com.benedsmith.DigitalWallet.models;

import com.benedsmith.DigitalWallet.enums.TransactionType;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequestBody implements Serializable {

    @NotNull
    private String walletId;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private BigDecimal amount;

}
