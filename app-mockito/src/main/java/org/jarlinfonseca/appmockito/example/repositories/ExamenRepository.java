package org.jarlinfonseca.appmockito.example.repositories;

import org.jarlinfonseca.appmockito.example.models.Examen;

import java.util.List;

public interface ExamenRepository {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
