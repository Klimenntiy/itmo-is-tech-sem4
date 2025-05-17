package org.example.api_gateway.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long ownerId;
    private double balance;
    private List<TransactionHistoryDTO> transactions;
}
