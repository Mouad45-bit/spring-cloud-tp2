package org.example.accountservice.repository;

import org.example.accountservice.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<BankAccount, String> {
}