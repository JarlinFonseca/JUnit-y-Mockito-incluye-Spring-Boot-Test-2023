package org.jarlinfonseca.appmockito.example.repositories;

import org.jarlinfonseca.appmockito.example.models.Examen;


import java.util.Arrays;
import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository{
    @Override
    public List<Examen> findAll() {
        return Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6l, "Lenguaje"),
                new Examen(7l, "Historia"));
    }
}
