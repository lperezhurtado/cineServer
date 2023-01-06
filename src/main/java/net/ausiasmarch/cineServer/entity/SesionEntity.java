package net.ausiasmarch.cineServer.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sesion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SesionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;
    
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public List<EntradaEntity> getEntradas() {
        return entradas;
    }

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
    
}
