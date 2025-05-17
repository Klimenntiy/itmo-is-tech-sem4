package org.example.Klimenntiy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.Klimenntiy.dto.AccountDTO;
import org.example.Klimenntiy.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create an account for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account successfully created")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestParam String login) {
        accountService.createAccount(login);
        return ResponseEntity.ok("Account created for user: " + login);
    }

    @Operation(summary = "Deposit money into an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount")
    })
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> depositMoney(
            @PathVariable Long accountId,
            @RequestParam double amount
    ) {
        accountService.depositMoney(accountId, amount);
        return ResponseEntity.ok("Deposited " + amount + " to account " + accountId);
    }

    @Operation(summary = "Withdraw money from an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid amount")
    })
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdrawMoney(
            @PathVariable Long accountId,
            @RequestParam double amount
    ) {
        accountService.withdrawMoney(accountId, amount);
        return ResponseEntity.ok("Withdrew " + amount + " from account " + accountId);
    }

    @Operation(summary = "Get balance of an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long accountId) {
        double balance = accountService.showBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @Operation(summary = "Get all accounts for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{login}")
    public ResponseEntity<List<AccountDTO>> getUserAccounts(@PathVariable String login) {
        List<AccountDTO> accounts = accountService.getUserAccounts(login);
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary = "Get all accounts in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    })
    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
