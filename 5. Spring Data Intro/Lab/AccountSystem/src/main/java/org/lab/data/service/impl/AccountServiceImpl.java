package org.lab.data.service.impl;

import org.lab.data.entites.Account;
import org.lab.data.entites.User;
import org.lab.data.repositories.AccountRepository;
import org.lab.data.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Integer id) {
        Optional<Account> optional = this.accountRepository.findById(id);
        //ако optional има стойност, откривал съм акаунт
        if (optional.isPresent()){
            Account account = optional.get();
            //само ако имам наличните пари, мога да ги изтегля
            if(account.getBalance().compareTo(money) >= 0) {
                account.setBalance(account.getBalance().subtract(money));
                this.accountRepository.saveAndFlush(account);
            }
        }
    }

    @Override
    public void transferMoney(BigDecimal money, Integer id) {
        Optional<Account> optional = this.accountRepository.findById(id);
        //ако optional има стойност, откривал съм акаунт
        if (optional.isPresent()){
            Account account = optional.get();
            account.setBalance(account.getBalance().add(money));
            this.accountRepository.saveAndFlush(account);
        }
    }

    @Override
    public void addAccount(Account account) {
        this.accountRepository.saveAndFlush(account);
    }


}

