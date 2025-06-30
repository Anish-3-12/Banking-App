package com.bankApp.bankingApp.service;

import com.bankApp.bankingApp.dto.AccountDto;
import com.bankApp.bankingApp.dto.TransactionDto;
import com.bankApp.bankingApp.dto.TransferFundDto;

import java.util.List;


public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

    void transferFunds(TransferFundDto transferFundDto);

    List<TransactionDto> getAccountTransactions(Long accountId);
}
