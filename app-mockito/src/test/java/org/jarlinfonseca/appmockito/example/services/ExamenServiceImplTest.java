package org.jarlinfonseca.appmockito.example.services;

import org.jarlinfonseca.appmockito.example.models.Examen;
import org.jarlinfonseca.appmockito.example.repositories.ExamenRepository;
import org.jarlinfonseca.appmockito.example.repositories.ExamenRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest {

    @Test
    void findExamenPorNombre() {
        ExamenRepository repository = new ExamenRepositoryImpl();
        ExamenService service = new ExamenServiceImpl(repository);
        Examen examen = service.findExamenPorNombre("Matemáticas");
        List<Examen> datos =  Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6l, "Lenguaje"),
                new Examen(7l, "Historia"));
        
        assertNotNull(examen);
        assertEquals(5L, examen.getId());
        assertEquals("Matemáticas", examen.getNombre());
    }
}