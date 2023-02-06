package net.ausiasmarch.cineServer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.EntradaEntity;
import net.ausiasmarch.cineServer.entity.SalaEntity;
import net.ausiasmarch.cineServer.entity.SesionEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.exceptions.ValidationException;
//import net.ausiasmarch.cineServer.helper.RandomHelper;
import net.ausiasmarch.cineServer.helper.ValidationHelper;
import net.ausiasmarch.cineServer.repository.EntradaRepository;
import net.ausiasmarch.cineServer.repository.SesionRepository;

//import java.time.LocalDateTime;
import java.util.List;

@Service
public class SesionService {

    @Autowired
    AuthService authService;

    @Autowired
    SesionRepository sesionRepo;

    @Autowired
    EntradaRepository entradaRepo;

    @Autowired
    SalaService salaService;

    @Autowired
    TarifaService tarifaService;

    @Autowired
    PeliculaService peliculaService;

    public void validateID(Long id) {
        if(!sesionRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra sesion con el id "+id);
        }
    }

    public void validateSesion(SesionEntity sesion) {
        salaService.validateID(sesion.getSala().getId());
        peliculaService.validateID(sesion.getPelicula().getId());
        tarifaService.validateID(sesion.getTarifa().getId());
    }

    public Long count() {
        return sesionRepo.count();
    }
    //GETPAGE revisar por si acaso
    /*public Page<SesionEntity> getPage(Pageable pageable, Long id_sala, Long id_pelicula, Long id_tarifa) {
        ValidationHelper.validateRPP(pageable.getPageSize());

        if (id_sala == null && id_pelicula != null && id_tarifa != null) {
            return sesionRepo.findByPeliculaIdAndTarifaId(id_pelicula, id_tarifa, pageable);
        }
        else if(id_sala != null && id_pelicula != null && id_tarifa == null) {
            return sesionRepo.findBySalaIdAndPeliculaId(id_sala, id_pelicula, pageable);
        }
        else if(id_sala != null && id_pelicula == null && id_tarifa != null) {
            return sesionRepo.findBySalaIdAndTarifaId(id_sala, id_tarifa, pageable);
        }
        else if(id_sala != null && id_pelicula != null && id_tarifa != null) {
            return sesionRepo.findBySalaIdAndPeliculaIdAndTarifaId(id_sala, id_pelicula, id_tarifa, pageable);
        }
        else if(id_sala != null && id_pelicula == null && id_tarifa == null) {
            return sesionRepo.findBySalaId(id_sala, pageable);
        }
        else if(id_sala == null && id_pelicula != null && id_tarifa == null) {
            return sesionRepo.findByPeliculaId(id_pelicula, pageable);
        }
        else if(id_sala == null && id_pelicula == null && id_tarifa != null) {
            return sesionRepo.findByTarifaId(id_tarifa, pageable);
        }
        else{
            return sesionRepo.findAll(pageable);
        }
    }*/    
    //GETPAGE CON FILTRO revisar por si acaso
    public Page<SesionEntity> getPage(Pageable pageable, Long id_sala, Long id_pelicula, Long id_tarifa) {
        ValidationHelper.validateRPP(pageable.getPageSize());

        if (id_sala == null && id_pelicula != null && id_tarifa != null) {
            return sesionRepo.findByPeliculaIdAndTarifaId(id_pelicula, id_tarifa, pageable);
        }
        else if(id_sala != null && id_pelicula != null && id_tarifa == null) {
            return sesionRepo.findBySalaIdAndPeliculaId(id_sala, id_pelicula, pageable);
        }
        else if(id_sala != null && id_pelicula == null && id_tarifa != null) {
            return sesionRepo.findBySalaIdAndTarifaId(id_sala, id_tarifa, pageable);
        }
        else if(id_sala != null && id_pelicula != null && id_tarifa != null) {
            return sesionRepo.findBySalaIdAndPeliculaIdAndTarifaId(id_sala, id_pelicula, id_tarifa, pageable);
        }
        else if(id_sala != null && id_pelicula == null && id_tarifa == null) {
            return sesionRepo.findBySalaId(id_sala, pageable);
        }
        else if(id_sala == null && id_pelicula != null && id_tarifa == null) {
            return sesionRepo.findByPeliculaId(id_pelicula, pageable);
        }
        else if(id_sala == null && id_pelicula == null && id_tarifa != null) {
            return sesionRepo.findByTarifaId(id_tarifa, pageable);
        }
        else{
            return sesionRepo.findAll(pageable);
        }
    }

    public SesionEntity get(Long id) {
        validateID(id);
        return sesionRepo.getReferenceById(id);
    }
    //CREATE llamará a crearEntradas y creará total de entradas segun alto y ancho de sala de la sesion
    @Transactional
    public Long create(SesionEntity newSesion) {
        authService.onlyAdmins();
        validateSesion(newSesion);
        newSesion.setFechaHora(newSesion.getFechaHora().plusHours(0));

        SalaEntity sala = salaService.get(newSesion.getSala().getId());
        int anchoSala = sala.getAncho();
        int altoSala = sala.getAlto();
        Long idSesion = sesionRepo.save(newSesion).getId(); //AQUI GUARDA LA SESION Y LUEGO DEVULVE EL ID

        crearEntradas(anchoSala, altoSala, idSesion); //crea entradas asociadas a la sesion
        return idSesion;
    }

    @Transactional
    public Long update(SesionEntity updatedSesion) {
        validateID(updatedSesion.getId());

        SesionEntity actualSesion = sesionRepo.getReferenceById(updatedSesion.getId());
        actualSesion.setFechaHora(updatedSesion.getFechaHora());
        actualSesion.setPelicula(updatedSesion.getPelicula());
        actualSesion.setSala(updatedSesion.getSala());
        actualSesion.setTarifa(updatedSesion.getTarifa());

        return sesionRepo.save(actualSesion).getId();
    }

    public Long delete(Long id) {
        authService.onlyAdmins();
        validateID(id);

        borrarEntradas(id); //antes de eliminar la sesion, borra las entradas
        sesionRepo.deleteById(id);

        if(sesionRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }

    //CREA ENTRADAS ENTITY CON EL ALTO Y ANCHO DE LA SALA
    public void crearEntradas(int ancho, int alto, Long id_sesion) {
        
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                
                EntradaEntity newEntrada = new EntradaEntity();
                newEntrada.setId(0L);
                newEntrada.setEjeX(i);
                newEntrada.setEjeY(j);
                newEntrada.setLibre(true);
                newEntrada.setSesion(sesionRepo.getReferenceById(id_sesion));

                entradaRepo.save(newEntrada);
                //entradaService.create(newEntrada);
            }
        }
    }
    //Cuando se borre una sesion, se llamará a este método para borrar las entradas asociadas a la sesión borrada
    public void borrarEntradas(Long id_sesion) {

        //SesionEntity sesion = sesionRepo.getReferenceById(id_sesion);

        //LocalDateTime now = RandomHelper.randomDate();
        //LocalDateTime fechaSesion = sesion.getFechaHora();
        //ValidationHelper.validateFechaFinal(now, fechaSesion, "No se puede eliminar la sesión, hay entradas vendidas.");
        /*if(sesion.getEntradasCount() != 0) {
            throw new ValidationException("No se puede eliminar la sesión, hay entradas vendidas.");
        }*/
        List<EntradaEntity> entradas = entradaRepo.findBySesionId(id_sesion);
        
        for (int i = 0; i < entradas.size(); i++) {
            if (entradas.get(i).isLibre() == false) {
                throw new ValidationException("hay entradas vendidas en dicha sesión.");
            }
        }
        entradaRepo.deleteAll(entradas);
    }
}
