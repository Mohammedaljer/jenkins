package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    @Value("${encryption.aesKey}")
    private String aesKey;
    
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Sample accounts creation
        createAccount("mo", "mo@example.com", "password1").subscribe(
            account -> logger.info("Created account: {}", account.getName()),
            error -> logger.error("Error creating account", error)
        );

        createAccount("jan", "jan@example.com", "password2").subscribe(
            account -> logger.info("Created account: {}", account.getName()),
            error -> logger.error("Error creating account", error)
        );

        // Print all accounts
        printAllAccounts().subscribe(
            account -> logger.info("Account: ID: {}, Name: {}, Email: {}", account.getId(), account.getName(), account.getEmail()),
            error -> logger.error("Error fetching accounts", error)
        );
    }

    @Transactional
    public Mono<Account> createAccount(String name, String email, String password) {
        return accountRepository.createAccount(name, email, password);
    }

    public Mono<Account> getAccount(Long accountId) {
        return accountRepository.findAccountById(accountId);
    }

    public Flux<Account> printAllAccounts() {
        return accountRepository.findAllAccounts();
    }
}


  
/*
    public void printAllAccounts() {
        Flux<Account> accounts = accountRepository.findAll();

        accounts.subscribe(
            account -> {
                String decryptedEmail = account.getDecryptedEmail(); // Decrypt email
                logger.info("Account: {}, Email: {}", account.getName(), decryptedEmail);
            },
            error -> logger.error("Error retrieving accounts", error)
        );
    }*/



/*
@Service
public class AccountService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Mono<Account> createAccount(String name, String password) {
        return accountRepository.createAccount(name, password);
    }

    @Override
    public void run(String... args) throws Exception {
        createAccount("mo", "password1").subscribe(
            account -> logger.info("Created account: {}", account.getName()),
            error -> logger.error("Error creating account", error)
        );
        createAccount("jan", "password2").subscribe(
                account -> logger.info("Created account: {}", account.getName()),
                error -> logger.error("Error creating account", error)
            );
        // You can add more users here as needed
    }
}




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final TextEncryptor encryptor;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.encryptor = Encryptors.text("pass12345", "abcd1234abcd1234"); // Adjust these values as necessary
    }

    @Override
    public void run(String... args) {
        // You can place shared logic here, if there's any.
        logger.info("Service is running with active profile(s)");
    }
    @Bean
    @Profile("mariadb")
    public CommandLineRunner mariadbRunner() {
        return args -> {
            logger.info("Running with MariaDB profile");
            createAndPrintAccounts(); // Example operation
        };
    }
    @Bean
    @Profile("postgres")
    public CommandLineRunner postgresRunner() {
        return args -> {
            logger.info("Running with PostgreSQL profile");
            createAndPrintAccounts(); // Example operation
        };
    }

    private void createAndPrintAccounts() {
        // Replace this with your actual account creation logic
        createAccount("user1", "password1").subscribe(
            account -> logger.info("Created account: {}", account.getName()),
            error -> logger.error("Error creating account", error)
        );
     // Create user2
        createAccount("user2", "password2").subscribe(
            account -> logger.info("Created account: {}", account.getName()),
            error -> logger.error("Error creating account", error)
        );
        // To print all accounts with decrypted passwords
        printAllAccounts();
    }

    public Mono<Account> createAccount(String name, String password) {
        Account account = new Account();
        account.setName(name);
        account.setEncryptedPassword(encryptor.encrypt(password));
        return accountRepository.save(account);
    }

    public void printAllAccounts() {
        Flux<Account> accounts = accountRepository.findAll();

        accounts.subscribe(
            account -> {
                String decryptedPassword = encryptor.decrypt(account.getEncryptedPassword());
                logger.info("Account: {}, Password: {}", account.getName(), decryptedPassword);
            },
            error -> logger.error("Error retrieving accounts", error)
        );
    }
}

*/


    	

