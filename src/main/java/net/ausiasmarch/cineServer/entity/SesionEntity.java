package net.ausiasmarch.cineServer.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sesion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SesionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fechaHora;
    
    @OneToMany(mappedBy = "sesion", fetch = FetchType.LAZY)
    private final List<EntradaEntity> entradas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sala")
    private SalaEntity sala;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pelicula")
    private PeliculaEntity pelicula;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tarifa")
    private TarifaEntity tarifa;

    public SesionEntity() {
        this.entradas = new ArrayList<>();
    }

    public SesionEntity(Long id) {
        this.entradas = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /*public List<EntradaEntity> getEntradas() {
        return entradas;
    }*/

    public int getEntradasCount() {
        return entradas.size();
    }

    public TarifaEntity getTarifa() {
        return tarifa;
    }

    public void setTarifa(TarifaEntity tarifa) {
        this.tarifa = tarifa;
    }

    public SalaEntity getSala() {
        return sala;
    }

    public void setSala(SalaEntity sala) {
        this.sala = sala;
    }

    public PeliculaEntity getPelicula() {
        return pelicula;
    }

    public void setPelicula(PeliculaEntity pelicula) {
        this.pelicula = pelicula;
    }

    @PreRemove
    public void nullify() {
        this.entradas.forEach(c -> c.setSesion(null));
    }
    
}
