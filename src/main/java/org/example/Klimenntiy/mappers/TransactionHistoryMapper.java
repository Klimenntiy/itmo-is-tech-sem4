package org.example.Klimenntiy.mappers;

import org.example.Klimenntiy.dto.TransactionHistoryDTO;
import org.example.Klimenntiy.entities.TransactionHistory;

public class TransactionHistoryMapper {
    public static TransactionHistoryDTO toDTO(TransactionHistory tx) {
        return new TransactionHistoryDTO(
                tx.getType(),
                tx.getAmount(),
                tx.getNewBalance()
        );
    }
}
