package com.jarlinfonseca.test.springboot.app.services;

import com.jarlinfonseca.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {

    Cuenta findById(Long id);
    int revisarTotalTransferencia(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);
}
