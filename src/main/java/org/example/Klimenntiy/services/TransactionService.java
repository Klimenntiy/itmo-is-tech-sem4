package org.example.Klimenntiy.services;

import org.example.Klimenntiy.dto.TransactionHistoryDTO;
import org.example.Klimenntiy.entities.Account;
import org.example.Klimenntiy.entities.TransactionHistory;
import org.example.Klimenntiy.entities.User;
import org.example.Klimenntiy.exceptions.AccountNotFoundException;
import org.example.Klimenntiy.exceptions.InsufficientFundsException;
import org.example.Klimenntiy.exceptions.UserNotFoundException;
import org.example.Klimenntiy.mappers.TransactionHistoryMapper;
import org.example.Klimenntiy.repository.AccountRepository;
import org.example.Klimenntiy.repository.TransactionHistoryRepository;
import org.example.Klimenntiy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository,
                              UserRepository userRepository,
                              TransactionHistoryRepository transactionHistoryRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Transactional
    public void transferMoney(Long senderId, Long receiverId, double amount) {
        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found."));
        Account receiver = accountRepository.findById(receiverId)
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found."));

        User senderUser = userRepository.findByLogin(sender.getOwnerLogin())
                .orElseThrow(() -> new UserNotFoundException("Sender user not found."));
        User receiverUser = userRepository.findByLogin(receiver.getOwnerLogin())
                .orElseThrow(() -> new UserNotFoundException("Receiver user not found."));

        double commissionRate = determineCommissionRate(senderUser, receiverUser);
        double commission = amount * commissionRate;

        if (!sender.transferTo(receiver, amount, commission)) {
            throw new InsufficientFundsException("Insufficient funds.");
        }

        if (commission > 0) {
            transactionHistoryRepository.save(new TransactionHistory("Commission", commission, sender.getBalance(), sender));
        }

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }

    private double determineCommissionRate(User sender, User receiver) {
        if (sender.getLogin().equals(receiver.getLogin())) {
            return 0.0;
        }
        return sender.isFriend(receiver) ? 0.03 : 0.10;
    }

    @Transactional(readOnly = true)
    public List<TransactionHistoryDTO> getTransactionHistory(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        return account.getTransactions().stream()
                .map(TransactionHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionHistoryDTO> getTransactionsWithFilters(Long accountId, String type) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        return account.getTransactions().stream()
                .filter(tx -> type == null || tx.getType().equalsIgnoreCase(type))
                .map(TransactionHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
