package com.runaitec.credimacpato.usecase;

public interface AccountUseCase {
    void openAccount(Long usuarioId);
    void closeAccount(Long cuentaId);
    void blockAccount(Long cuentaId);
}