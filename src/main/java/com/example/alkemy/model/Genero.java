package com.example.alkemy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Genero {
    
    @Id
    @Column(name="idgenero")
    private Integer id;
    private String titulo;
    
}
