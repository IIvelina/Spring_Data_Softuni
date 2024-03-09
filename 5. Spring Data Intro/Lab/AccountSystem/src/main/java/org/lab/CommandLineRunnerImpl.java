package org.lab;

import org.lab.data.entites.Account;
import org.lab.data.entites.User;

import org.lab.data.service.AccountService;
import org.lab.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private final UserService userService;

    @Autowired
    private final AccountService accountService;

    public CommandLineRunnerImpl(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Ivan", 26);
        this.userService.register(user);
        Account account = new Account(BigDecimal.valueOf(10000), user);
        this.accountService.addAccount(account);

        this.accountService.withdrawMoney(BigDecimal.valueOf(2000), 1);
        this.accountService.transferMoney(BigDecimal.valueOf(4000), 1);
    }
}
