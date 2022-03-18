package com.example.alkemy.repo;

import com.example.alkemy.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Integer>{
    
}
