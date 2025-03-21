package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.Results.Exceptions.AccountNotFoundException;
import org.example.dto.TransactionHistoryDTO;
import org.example.entities.Account;
import org.example.entities.TransactionHistory;
import org.example.entities.User;
import org.example.repositories.AccountRepository;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling financial transactions between accounts.
 */
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    /**
     * Transfers money from one account to another, applying commission if necessary.
     *
     * @param senderId   ID of the sender's account.
     * @param receiverId ID of the receiver's account.
     * @param amount     Amount to be transferred.
     * @throws AccountNotFoundException if one or both accounts are not found.
     */
    public void transferMoney(String senderId, String receiverId, double amount) {
        Account sender = accountRepository.getAccount(senderId);
        Account receiver = accountRepository.getAccount(receiverId);

        if (sender == null || receiver == null) {
            throw new AccountNotFoundException("One or both accounts not found.");
        }

        User senderUser = userRepository.getUser(sender.getOwnerLogin());
        User receiverUser = userRepository.getUser(receiver.getOwnerLogin());

        double commissionRate = determineCommissionRate(senderUser, receiverUser);
        double commission = amount * commissionRate;
        double totalAmount = amount + commission;

        if (!sender.transferTo(receiver, amount, commission)) {
            System.out.printf("Transaction failed: Insufficient funds. Total required: %.2f (including commission: %.2f)%n",
                    totalAmount, commission);
            return;
        }

        if (commission > 0) {
            accountRepository.saveTransactionHistory(new TransactionHistory("Commission", commission, sender.getBalance(), sender));
        }

        accountRepository.saveAccount(sender);
        accountRepository.saveAccount(receiver);
    }

    /**
     * Determines the commission rate for a transaction based on the relationship between users.
     *
     * @param sender   The sender's user profile.
     * @param receiver The receiver's user profile.
     * @return The commission rate (0.0 - 10.0%).
     */
    private double determineCommissionRate(User sender, User receiver) {
        return sender.getLogin().equals(receiver.getLogin()) ? 0.0 : sender.isFriend(receiver) ? 0.03 : 0.10;
    }

    /**
     * Retrieves the transaction history of a specific account.
     *
     * @param accountId The ID of the account.
     * @return A list of transaction history records as DTOs.
     * @throws AccountNotFoundException if the account is not found.
     */
    public List<TransactionHistoryDTO> getTransactionHistory(String accountId) {
        Account account = accountRepository.getAccount(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        return account.getTransactions().stream()
                .map(tx -> new TransactionHistoryDTO(tx.getType(), tx.getAmount(), tx.getNewBalance()))
                .collect(Collectors.toList());
    }
}
