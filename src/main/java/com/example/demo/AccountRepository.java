package com.example.demo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

    @Query("INSERT INTO accounts (name, email, encrypted_password) VALUES (:name, ENCRYPT('AES', 'aesKey', STRINGTOUTF8(:email)), HASH('SHA-256', STRINGTOUTF8(:password)))")
    Mono<Account> createAccount(String name, String email, String password);

    @Query("SELECT id, name, DECRYPT('AES', 'aesKey', email) as email, encrypted_password FROM accounts WHERE id = :id")
    Mono<Account> findAccountById(Long id);

    @Query("SELECT id, name, DECRYPT('AES', 'aesKey', email) as email, encrypted_password FROM accounts")
    Flux<Account> findAllAccounts();
}
