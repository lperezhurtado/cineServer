package net.ausiasmarch.cineServer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.EntradaEntity;
//import net.ausiasmarch.cineServer.entity.SesionEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.repository.EntradaRepository;

@Service
public class EntradaService {
    
    @Autowired
    AuthService authService;

    @Autowired
    EntradaRepository entradaRepo;

    @Autowired
    SalaService salaService;

    //@Autowired
    //SesionService sesionService;


    public void validateID(Long id) {
        if (!entradaRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra butaca con id  " + id);
        }
    }

    public void validateCoor(int x, int y, int fila, int col) {
        

        //entrada.getSesion().getId().get;
    }

    public void validate(EntradaEntity entradaEntity) {

    }

    public Long count() {
        return entradaRepo.count();
    }

    public Page<EntradaEntity> getPage(Pageable pageable, Long id_sesion) {
        if (id_sesion == null) {
            return entradaRepo.findAll(pageable);
        } else {
            return entradaRepo.findBySesionId(id_sesion, pageable);
        }
    }

    public EntradaEntity get(Long id) {
        validateID(id);
        return entradaRepo.getReferenceById(id);
    }

    public Long create(EntradaEntity newEntrada) {
        authService.onlyAdmins();
        newEntrada.setId(0L);
        return entradaRepo.save(newEntrada).getId();
    }

    @Transactional
    public Long update(EntradaEntity updatedEntrada) {
        validateID(updatedEntrada.getId());

        EntradaEntity actualEntrada = entradaRepo.getReferenceById(updatedEntrada.getId());
        actualEntrada.setEjeX(updatedEntrada.getEjeX());
        actualEntrada.setEjeY(updatedEntrada.getEjeY());
        actualEntrada.setLibre(updatedEntrada.isLibre());
        actualEntrada.setSesion(updatedEntrada.getSesion());

        return entradaRepo.save(actualEntrada).getId();
    }

    public Long delete(Long id) {
        authService.onlyAdmins();
        validateID(id);
        entradaRepo.deleteById(id);

        if(entradaRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }
    
}
