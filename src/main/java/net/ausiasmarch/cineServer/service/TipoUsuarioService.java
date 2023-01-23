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
    AuthService authService;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepo;

    public void validate(Long id) {
        if (!tipoUsuarioRepo.existsById(id)) {
            throw new ResourceNotFound("No existe este tipo de usuario");
        }
    }

    //GET Method
    public TipoUsuarioEntity get(Long id) {
        authService.onlyAdmins();
        validate(id);
        return tipoUsuarioRepo.getReferenceById(id);
    }

    public Long count() {
        authService.onlyAdmins();
        return tipoUsuarioRepo.count();
    }

    public Page<TipoUsuarioEntity> getPage(Pageable pageable) {
        authService.onlyAdmins();
        Page<TipoUsuarioEntity> page = tipoUsuarioRepo.findAll(pageable);

        return page;
    } 

    public Long create(TipoUsuarioEntity newTipoUsuario) {
        authService.onlyAdmins();
        newTipoUsuario.setId(0L);
        return tipoUsuarioRepo.save(newTipoUsuario).getId();
    }

    public Long delete(Long id) {
        authService.onlyAdmins();
        validate(id);
        TipoUsuarioEntity tipoU = tipoUsuarioRepo.getReferenceById(id);
        //si hay usuarios con el tipousuario a borrar, se cambiarán a tipousuario = 2
        if (tipoU.getUsuariosCount() != 0) {
            TipoUsuarioEntity tipoUNew = tipoUsuarioRepo.getReferenceById(2L);
            tipoU.nullify(tipoUNew);
        }
        tipoUsuarioRepo.deleteById(id);
        return id;
    }

    public Long update(TipoUsuarioEntity tipoUsuarioEntity) {
        authService.onlyAdmins();
        return tipoUsuarioRepo.save(tipoUsuarioEntity).getId();
    }
}
