package com.benedsmith.DigitalWallet.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class WalletRequestBody implements Serializable {
    @NotNull
    @JsonProperty("max_deposit")
    private BigDecimal maxDeposit;

    @NotNull
    @JsonProperty("max_withdraw")
    private BigDecimal maxWithdraw;

    @NotNull
    @JsonProperty("min_transfer")
    private BigDecimal minTransfer;

}
