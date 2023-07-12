package com.jarlinfonseca.test.springboot.app.services;

import com.jarlinfonseca.test.springboot.app.models.Banco;
import com.jarlinfonseca.test.springboot.app.models.Cuenta;
import com.jarlinfonseca.test.springboot.app.repositories.BancoRepository;
import com.jarlinfonseca.test.springboot.app.repositories.CuentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class CuentaServiceImpl implements CuentaService {
    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencia(Long bancoId) {
        Banco banco= bancoRepository.findById(bancoId);
        return banco.getTotalTransferencia();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto,
                           Long bancoId) {

        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRepository.update(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaRepository.update(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencias);
        bancoRepository.update(banco);
    }
}