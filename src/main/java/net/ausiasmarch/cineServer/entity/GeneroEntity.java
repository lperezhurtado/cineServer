package net.ausiasmarch.cineServer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "genero")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GeneroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "genero", fetch = FetchType.LAZY)
    private final List<PeliculaEntity> peliculas;

    public GeneroEntity() {
        this.peliculas = new ArrayList<>();
    }

    public GeneroEntity(Long id, String nombre) {
        this.peliculas = new ArrayList<>();
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPeliculasCount() {
        return peliculas.size();
    }

    @PreRemove
    public void nullify() {
        this.peliculas.forEach(c -> c.setGenero(null));
    }
    
}
