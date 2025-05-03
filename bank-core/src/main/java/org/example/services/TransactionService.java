package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionHistoryDTO;
import org.example.entities.Account;
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
     */
    public void transferMoney(String senderId, String receiverId, double amount) {
        Account sender = accountRepository.getAccount(senderId);
        Account receiver = accountRepository.getAccount(receiverId);

        if (sender == null || receiver == null) {
            System.out.println("Transaction failed: One or both accounts not found.");
            return;
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

        System.out.printf("Transaction successful. Total debited: %.2f (including commission: %.2f)%n",
                totalAmount, commission);
    }

    /**
     * Determines the commission rate for a transaction based on the relationship between users.
     *
     * @param sender   The sender's user profile.
     * @param receiver The receiver's user profile.
     * @return The commission rate (0.0 - 10.0%).
     */
    private double determineCommissionRate(User sender, User receiver) {
        if (sender.getLogin().equals(receiver.getLogin())) {
            return 0.0;
        }
        return sender.isFriend(receiver) ? 0.03 : 0.10;
    }

    /**
     * Retrieves the transaction history of a specific account.
     *
     * @param accountId The ID of the account.
     * @return A list of transaction history records as DTOs.
     */
    public List<TransactionHistoryDTO> getTransactionHistory(String accountId) {
        Account account = accountRepository.getAccount(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return List.of();
        }

        return account.getTransactionHistory().stream()
                .map(tx -> new TransactionHistoryDTO(tx.type(), tx.amount(), tx.newBalance()))
                .collect(Collectors.toList());
    }
}
