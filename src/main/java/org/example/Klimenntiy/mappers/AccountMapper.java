package org.example.Klimenntiy.mappers;

import org.example.Klimenntiy.dto.AccountDTO;
import org.example.Klimenntiy.entities.Account;

public class AccountMapper {
    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getOwnerLogin(),
                account.getBalance()
        );
    }
}
