package org.jarlinfonseca.appmockito.example.services;

import org.jarlinfonseca.appmockito.example.models.Examen;
import org.jarlinfonseca.appmockito.example.repositories.ExamenRepository;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{

    private ExamenRepository examenRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Examen findExamenPorNombre(String nombre) {
       Optional<Examen> examenOptional = examenRepository.findAll()
               .stream()
               .filter(e -> e.getNombre().contains(nombre))
               .findFirst();
       Examen examen = null;
       if(examenOptional.isPresent()){
           examen = examenOptional.orElseThrow();
       }
        return examen;
    }
}
