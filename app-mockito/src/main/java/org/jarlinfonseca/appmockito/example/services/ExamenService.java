package org.jarlinfonseca.appmockito.example.services;

import org.jarlinfonseca.appmockito.example.models.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);
    Examen findExamenPorNombreConPreguntas(String nombre);
}
