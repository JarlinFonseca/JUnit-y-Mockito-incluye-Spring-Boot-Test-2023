package com.jarlinfonseca.test.springboot.app.repositories;

import com.jarlinfonseca.test.springboot.app.models.Cuenta;

import java.util.List;

public interface CuentaRepository {

    List<Cuenta> findAll();

    Cuenta findById(Long id);

    void update(Cuenta cuenta);
}
