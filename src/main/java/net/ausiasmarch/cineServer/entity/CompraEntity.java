package net.ausiasmarch.cineServer.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "compra")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompraEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double precio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fecha;

    private int descuentoUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_entrada")
    private EntradaEntity entrada;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_factura")
    private FacturaEntity factura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getDescuentoUsuario() {
        return descuentoUsuario;
    }

    public void setDescuentoUsuario(int descuentoUsuario) {
        this.descuentoUsuario = descuentoUsuario;
    }

    public EntradaEntity getEntrada() {
        return entrada;
    }

    public void setEntrada(EntradaEntity entrada) {
        this.entrada = entrada;
    }

    public FacturaEntity getFactura() {
        return factura;
    }

    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }

    
}
