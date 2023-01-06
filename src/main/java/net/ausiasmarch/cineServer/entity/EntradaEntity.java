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
@Table(name = "entrada")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntradaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ejeX;
    private int ejeY;
    private boolean ocupada;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sesion")
    private SesionEntity sesion;

    @OneToMany(mappedBy = "entrada", fetch = FetchType.LAZY)
    private final List<CompraEntity> compras;

    public EntradaEntity() {
        this.compras = new ArrayList<>();
    }

    public EntradaEntity(Long id) {
        this.compras = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEjeX() {
        return ejeX;
    }

    public void setEjeX(int ejeX) {
        this.ejeX = ejeX;
    }

    public int getEjeY() {
        return ejeY;
    }

    public void setEjeY(int ejeY) {
        this.ejeY = ejeY;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public SesionEntity getSesion() {
        return sesion;
    }

    public void setSesion(SesionEntity sesion) {
        this.sesion = sesion;
    }

    public List<CompraEntity> getCompras() {
        return compras;
    }

    public int getComprasCount() {
        return compras.size();
    }

    
}
