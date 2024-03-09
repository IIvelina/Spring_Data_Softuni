package org.lab.data.service;

import org.lab.data.entites.Account;
import org.lab.data.entites.User;

import java.math.BigDecimal;

public interface AccountService {

    void withdrawMoney(BigDecimal money, Integer id);
    void transferMoney(BigDecimal money, Integer id);

    void addAccount(Account account);
}
