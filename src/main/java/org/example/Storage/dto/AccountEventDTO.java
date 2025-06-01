package org.example.Storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEventDTO {
    private Long id;
    private Long ownerId;
    private double balance;
}
