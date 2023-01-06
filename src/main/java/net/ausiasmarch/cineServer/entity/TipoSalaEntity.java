package net.ausiasmarch.cineServer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tiposala")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoSalaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "tipoSala", fetch = FetchType.LAZY)
    private final List<SalaEntity> salas;

    public TipoSalaEntity() {
        this.salas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<SalaEntity> getSalas() {
        return salas;
    }

    public int getSalasCount() {
        return salas.size();
    }
    
}
