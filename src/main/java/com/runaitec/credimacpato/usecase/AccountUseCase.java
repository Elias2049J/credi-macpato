package com.runaitec.credimacpato.usecase;

import com.runaitec.credimacpato.dto.AccountDTO;
import com.runaitec.credimacpato.entity.AccountState;

public interface AccountUseCase {
    AccountDTO openAccount(Long userId);
    AccountDTO closeAccount(Long userId);
    AccountState blockAccount(Long accountId);
}