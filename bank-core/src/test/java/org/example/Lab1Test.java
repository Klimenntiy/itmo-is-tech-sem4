package org.example;

import org.example.Results.SuccessResult;
import org.example.entities.Account;
import org.example.Results.Exceptions.InsufficientFundsException;
import org.example.repositories.AccountRepository;
import org.example.services.AccountService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Lab1Test {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @org.junit.jupiter.api.Test
    void withdrawMoney_SufficientBalance_ShouldUpdateBalance() {
        String accountId = "acc123";
        Account account = new Account("user123");
        account.deposit(500);
        when(accountRepository.getAccount(accountId)).thenReturn(account);
        SuccessResult result = accountService.withdrawMoney(accountId, 200);

        assertEquals(300, account.getBalance());
        verify(accountRepository).saveAccount(account);
        assertEquals("Withdrawal successful! New balance: 300.0", result.getMessage());
    }

    @org.junit.jupiter.api.Test
    void withdrawMoney_InsufficientBalance_ShouldThrowException() {
        String accountId = "acc123";
        Account account = new Account("user123");
        account.deposit(100);
        when(accountRepository.getAccount(accountId)).thenReturn(account);

        assertThrows(InsufficientFundsException.class, () -> accountService.withdrawMoney(accountId, 200));
        verify(accountRepository, never()).saveAccount(account);
    }

    @org.junit.jupiter.api.Test
    void depositMoney_ShouldUpdateBalance() {
        String accountId = "acc123";
        Account account = new Account("user123");
        account.deposit(100);
        when(accountRepository.getAccount(accountId)).thenReturn(account);
        SuccessResult result = accountService.depositMoney(accountId, 200);

        assertEquals(300, account.getBalance());
        verify(accountRepository).saveAccount(account);
        assertEquals("Deposit successful! New balance: 300.0", result.getMessage());
    }
}
