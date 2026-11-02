package org.example.accountservice.controllers;

import org.example.accountservice.clients.CustomerRestClient;
import org.example.accountservice.entities.BankAccount;
import org.example.accountservice.entities.Customer;
import org.example.accountservice.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    private final AccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    public AccountRestController(
            AccountRepository accountRepository,
            CustomerRestClient customerRestClient) {

        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<?> getBankAccountById(
            @PathVariable String accountId) {

        BankAccount bankAccount =
                accountRepository.findById(accountId).orElse(null);

        if (bankAccount == null) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("errorMessage", "account not found"));
        }

        Customer customer = customerRestClient
                .getCustomerById(bankAccount.getCustomerId());

        bankAccount.setCustomer(customer);

        return ResponseEntity.ok(bankAccount);
    }

    @GetMapping("/accounts")
    public List<BankAccount> accountList() {
        return accountRepository.findAll();
    }
}