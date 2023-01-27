package net.ausiasmarch.cineServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.SalaEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.helper.ValidationHelper;
import net.ausiasmarch.cineServer.repository.SalaRepository;

@Service
public class SalaService {

    @Autowired
    AuthService authService;

    @Autowired
    SalaRepository salaRepo;

    @Autowired
    TipoSalaService tipoSalaService;

    public void validateID(Long id) {
        if (!salaRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra sala con id  " + id);
        }
    }

    public void validateSala(SalaEntity salaEntity) {
        ValidationHelper.validateInt(salaEntity.getAlto(), 1, 20, "Alto no válido");
        ValidationHelper.validateInt(salaEntity.getAncho(), 1, 20, "Ancho no válido");
        tipoSalaService.validateID(salaEntity.getTiposala().getId());
    }
    //COUNT
    public Long count() {
        //authService.onlyAdmins();
        return salaRepo.count();
    }
    //GETPAGE
    public Page<SalaEntity> getPage(Pageable pageable, Long id_tipoSala) {
        if (id_tipoSala == null) {
            return salaRepo.findAll(pageable);
        } else {
            return salaRepo.findByTiposalaId(id_tipoSala, pageable);
        }
    }
    //GET
    public SalaEntity get(Long id) {
        validateID(id);
        return salaRepo.getReferenceById(id);
    }
    //CREATE
    public Long create(SalaEntity newSala) {
        authService.onlyAdmins();
        validateSala(newSala);
        newSala.setId(0L);
        return salaRepo.save(newSala).getId();
    }
    //UPDATE
    public Long update(SalaEntity updatedSala) {
        authService.onlyAdmins();
        validateID(updatedSala.getId());
        validateSala(updatedSala);

        SalaEntity actualSala = salaRepo.getReferenceById(updatedSala.getId());
        actualSala.setAlto(updatedSala.getAlto());
        actualSala.setAncho(updatedSala.getAncho());
        actualSala.setTipoSala(updatedSala.getTiposala());

        return salaRepo.save(actualSala).getId();
    }
    //DELETE
    public Long delete(Long id) {
        authService.onlyAdmins();
        validateID(id);
        salaRepo.deleteById(id);

        if(salaRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }
    
}
