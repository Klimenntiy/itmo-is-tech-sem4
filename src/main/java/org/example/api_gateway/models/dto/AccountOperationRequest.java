package org.example.api_gateway.models.dto;

import lombok.Data;

@Data
public class AccountOperationRequest {

    private Long accountId;

    private Long targetAccountId;

    private Double amount;

    private String operationType;

}
