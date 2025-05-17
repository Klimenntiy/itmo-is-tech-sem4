package org.example.Klimenntiy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.Klimenntiy.dto.TransactionHistoryDTO;
import org.example.Klimenntiy.dto.TransferRequest;
import org.example.Klimenntiy.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionHistoryController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionHistoryController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Transfer money from one account to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer completed successfully"),
            @ApiResponse(responseCode = "404", description = "One of the accounts was not found"),
            @ApiResponse(responseCode = "400", description = "Insufficient funds or invalid amount")
    })
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest transferMoneyDTO) {
        transactionService.transferMoney(
                transferMoneyDTO.senderId(),
                transferMoneyDTO.receiverId(),
                transferMoneyDTO.amount()
        );
        return ResponseEntity.ok("Transfer completed successfully");
    }

    @Operation(summary = "Get transaction history for a given account ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistory(@PathVariable Long accountId) {
        List<TransactionHistoryDTO> history = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(history);
    }

    @Operation(summary = "Get transactions with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionsWithFilters(
            @RequestParam Long accountId,
            @RequestParam(required = false) String type) {
        List<TransactionHistoryDTO> transactions = transactionService.getTransactionsWithFilters(accountId, type);
        return ResponseEntity.ok(transactions);
    }
}
