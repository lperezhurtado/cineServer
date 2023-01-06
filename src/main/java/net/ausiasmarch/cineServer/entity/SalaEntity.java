package net.ausiasmarch.cineServer.entity;

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
@Table(name = "sala")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SalaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int alto;
    private int ancho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tiposala")
    private TipoSalaEntity tipoSala;

    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    private final List<SesionEntity> sesiones;


    public SalaEntity() {
        this.sesiones = new ArrayList<>();
    }
    public SalaEntity(Long id) {
        this.sesiones = new ArrayList<>();
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public TipoSalaEntity getTiposala() {
        return tipoSala;
    }

    public void setTipoSala(TipoSalaEntity tipoSala) {
        this.tipoSala = tipoSala;
    }

    public List<SesionEntity> getSesiones() {
        return sesiones;
    }

    public int getSesionesCOunt() {
        return sesiones.size();
    }
    
}
