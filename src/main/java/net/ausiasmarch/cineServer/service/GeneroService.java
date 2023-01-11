package net.ausiasmarch.cineServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.GeneroEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.repository.GeneroRepository;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepo;

    @Autowired
    AuthService authService;

    public void validate(Long id) {
        if (!generoRepo.existsById(id)) {
            throw new ResourceNotFound("No existe ningún género con id "+id);
        }
    }

    //GET Method
    public GeneroEntity get(Long id) {
        validate(id);
        return generoRepo.getReferenceById(id);
    }

    //COUNT METHOD
    public Long count() {
        return generoRepo.count();
    }

    //GET PAGE METHOD
    public Page<GeneroEntity> getPage(Pageable pageable, String filter) {

        Page<GeneroEntity> page;

        if (filter.equals(null) || filter.trim().isEmpty()) {
            page = generoRepo.findAll(pageable);
        } else {
            page = generoRepo.findByNombreIgnoreCaseContaining(filter, pageable);
        }
        return page;
    } 

    //CREATE METHOD
    public Long create(GeneroEntity newGenero) {
        authService.onlyAdmins();
        newGenero.setId(0L);
        return generoRepo.save(newGenero).getId();
    }

    //UPDATE METHOD
    public Long update(GeneroEntity generoEntity) {
        validate(generoEntity.getId());
        return generoRepo.save(generoEntity).getId();
    }

    //DELETE METHOD
    public Long delete(Long id) {
        validate(id);
        generoRepo.deleteById(id);
        return id;
    }
    
}
