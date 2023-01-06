package net.ausiasmarch.cineServer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipousuario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoUsuarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "tipousuario", fetch = FetchType.LAZY)
    private final List<UsuarioEntity> usuarios;

    public TipoUsuarioEntity() {
        this.usuarios = new ArrayList<>();
    }

    public TipoUsuarioEntity(Long id, String nombre) {
        this.usuarios = new ArrayList<>();
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getUsuariosCount() {
        return usuarios.size();
    }

    @PreRemove
    public void nullify() {
        this.usuarios.forEach(c -> c.setTipousuario(null));
    }

}
