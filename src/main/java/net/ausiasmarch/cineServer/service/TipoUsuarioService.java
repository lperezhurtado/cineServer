package net.ausiasmarch.cineServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.TipoUsuarioEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepo;

    public void validate(Long id) {
        if (!tipoUsuarioRepo.existsById(id)) {
            throw new ResourceNotFound("No existe este tipo de usuario");
        }
    }

    //GET Method
    public TipoUsuarioEntity get(Long id) {
        validate(id);
        return tipoUsuarioRepo.getReferenceById(id);
    }

    public Long count() {
        return tipoUsuarioRepo.count();
    }

    public Page<TipoUsuarioEntity> getPage(Pageable pageable) {

        Page<TipoUsuarioEntity> page = tipoUsuarioRepo.findAll(pageable);

        return page;
    } 

    public Long create(TipoUsuarioEntity newTipoUsuario) {
        newTipoUsuario.setId(0L);
        return tipoUsuarioRepo.save(newTipoUsuario).getId();
    }

    public Long delete(Long id) {
        tipoUsuarioRepo.deleteById(id);
        return id;
    }

    public Long update(TipoUsuarioEntity tipoUsuarioEntity) {
        return tipoUsuarioRepo.save(tipoUsuarioEntity).getId();
    }
}
