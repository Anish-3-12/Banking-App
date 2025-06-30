package com.bankApp.bankingApp.service.impl;

import com.bankApp.bankingApp.dto.AccountDto;
import com.bankApp.bankingApp.dto.TransactionDto;
import com.bankApp.bankingApp.dto.TransferFundDto;
import com.bankApp.bankingApp.entity.Account;
import com.bankApp.bankingApp.entity.Transaction;
import com.bankApp.bankingApp.exception.AccountException;
import com.bankApp.bankingApp.mapper.AccountMapper;
import com.bankApp.bankingApp.repository.AccountRepository;
import com.bankApp.bankingApp.repository.TransactionRepository;
import com.bankApp.bankingApp.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";

    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {

        Account account=accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist."));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account=accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist."));

        double total=account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);

        Transaction transaction= new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist."));

        if(account.getBalance()< amount){
            throw new RuntimeException("Insufficient amount");
        }

        double total= account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);

        Transaction transaction= new Transaction();
        transaction.setAccountId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);


        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {

        Account account=accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist."));

        accountRepository.deleteById(id);
    }

    @Override
    public void transferFunds(TransferFundDto transferFundDto) {

        //Retrive the account from which we need to send the amount
        Account fromAccount= accountRepository
                .findById(transferFundDto.fromAccountId())
                .orElseThrow(()-> new AccountException("Account does not exist"));

        //Retrive the account to which we need to send the amount
        Account toAccount=accountRepository
                .findById(transferFundDto.toAccountId())
                .orElseThrow(()-> new AccountException("Account does not exist"));

        if (fromAccount.getBalance()< transferFundDto.amount()){
            throw new RuntimeException("Insufficient Amount");
        }

        //Debit the amount fromAccount object
        fromAccount.setBalance(fromAccount.getBalance() - transferFundDto.amount());

        //Credit the amount to toAccount object
        toAccount.setBalance(toAccount.getBalance()+ transferFundDto.amount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction= new Transaction();
        transaction.setAccountId(transferFundDto.fromAccountId());
        transaction.setAmount(transferFundDto.amount());
        transaction.setTransactionType("TRANSFER");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long accountId) {

        List<Transaction> transactions = transactionRepository
                .findByAccountIdOrderByTimestampDesc(accountId);

        return transactions.stream()
                .map((transaction)-> convertEntityToDto(transaction))
                .collect(Collectors.toList());
    }

    private TransactionDto convertEntityToDto(Transaction transaction){
        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );
    }
}
