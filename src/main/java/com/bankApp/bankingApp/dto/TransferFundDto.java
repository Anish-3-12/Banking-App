package com.bankApp.bankingApp.dto;

public record TransferFundDto(Long fromAccountId,
                              Long toAccountId,
                              double amount) {
}
