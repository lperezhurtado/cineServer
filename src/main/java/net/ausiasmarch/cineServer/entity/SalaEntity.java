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
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private TipoSalaEntity tiposala;

    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    @JsonIgnore
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
        return tiposala;
    }

    public void setTipoSala(TipoSalaEntity tiposala) {
        this.tiposala = tiposala;
    }

    /*public List<SesionEntity> getSesiones() {
        return sesiones;
    }*/

    public int getSesionesCount() {
        return sesiones.size();
    }

    @PreRemove
    public void nullify() {
        this.sesiones.forEach(c -> c.setSala(null));
    }
}
