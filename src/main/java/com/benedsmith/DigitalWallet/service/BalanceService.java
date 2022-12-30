package com.benedsmith.DigitalWallet.service;

import java.math.BigDecimal;

public interface BalanceService {

    BigDecimal calculateBalance(String walletId);

}
