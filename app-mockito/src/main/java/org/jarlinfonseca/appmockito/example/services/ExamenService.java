package org.jarlinfonseca.appmockito.example.services;

import org.jarlinfonseca.appmockito.example.models.Examen;

public interface ExamenService {
    Examen findExamenPorNombre(String nombre);
}
