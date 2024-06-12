package com.example.demo;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class AccountController {
// test
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Mono<Account> createAccount(@RequestParam String name, @RequestParam String password, @RequestParam String email) {
        return accountService.createAccount(name, password, email);
    }

   
}
