package com.bankApp.bankingApp.repository;

import com.bankApp.bankingApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
