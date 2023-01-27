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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tarifa")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TarifaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private double precio;

    @OneToMany(mappedBy = "tarifa", fetch = FetchType.LAZY)
    @JsonIgnore
    private final List<SesionEntity> sesiones;

    public TarifaEntity() {
        this.sesiones = new ArrayList<>();
    }

    public TarifaEntity(Long id) {
        this.sesiones = new ArrayList<>();
        this.id = id;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getSesionesCount() {
        return sesiones.size();
    }

}
