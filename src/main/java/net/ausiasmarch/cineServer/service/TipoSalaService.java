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

    public void validateID(Long id) {
        if (!tipoSalaRepo.existsById(id)) {
            throw new ResourceNotFound("No existe este tipo de Sala");
        }
    }

    //GET Method
    public TipoSalaEntity get(Long id) {
        validateID(id);
        return tipoSalaRepo.getReferenceById(id);
    }

    //COUNT METHOD
    public Long count() {
        return tipoSalaRepo.count();
    }

    //GET PAGE METHOD
    public Page<TipoSalaEntity> getPage(Pageable pageable, String filter) {
        Page<TipoSalaEntity> page;

        if (filter == null || filter.trim().isEmpty()) {
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
        validateID(tipoSalaEntity.getId());
        TipoSalaEntity tipoSala = tipoSalaRepo.getReferenceById(tipoSalaEntity.getId());
        tipoSala.setNombre(tipoSalaEntity.getNombre());
        return tipoSalaRepo.save(tipoSala).getId();
    }

    //DELETE METHOD
    public Long delete(Long id) {
        validateID(id);
        tipoSalaRepo.deleteById(id);
        return id;
    }
}
