package net.ausiasmarch.cineServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.TipoSalaEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.repository.TipoSalaRepository;

@Service
public class TipoSalaService {
    
    @Autowired
    TipoSalaRepository tipoSalaRepo;

    @Autowired
    AuthService authService;

    public void validate(Long id) {
        if (!tipoSalaRepo.existsById(id)) {
            throw new ResourceNotFound("No existe este tipo de Sala");
        }
    }

    //GET Method
    public TipoSalaEntity get(Long id) {
        validate(id);
        return tipoSalaRepo.getReferenceById(id);
    }

    //COUNT METHOD
    public Long count() {
        return tipoSalaRepo.count();
    }

    //GET PAGE METHOD
    public Page<TipoSalaEntity> getPage(Pageable pageable, String filter) {

        Page<TipoSalaEntity> page;

        if (filter.equals(null) || filter.trim().isEmpty()) {
            page = tipoSalaRepo.findAll(pageable);
        }
        else{
            page = tipoSalaRepo.findByNombreIgnoreCaseContaining(filter, pageable);
        }
        return page;
    } 

    //CREATE METHOD
    public Long create(TipoSalaEntity newTipoSala) {
        authService.onlyAdmins();
        newTipoSala.setId(0L);
        return tipoSalaRepo.save(newTipoSala).getId();
    }

    //UPDATE METHOD
    public Long update(TipoSalaEntity tipoSalaEntity) {
        validate(tipoSalaEntity.getId());
        return tipoSalaRepo.save(tipoSalaEntity).getId();
    }

    //DELETE METHOD
    public Long delete(Long id) {
        validate(id);
        tipoSalaRepo.deleteById(id);
        return id;
    }
}
