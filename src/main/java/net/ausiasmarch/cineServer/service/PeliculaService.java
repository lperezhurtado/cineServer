package net.ausiasmarch.cineServer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.PeliculaEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.helper.ValidationHelper;
import net.ausiasmarch.cineServer.repository.GeneroRepository;
import net.ausiasmarch.cineServer.repository.PeliculaRepository;

@Service
public class PeliculaService {

    @Autowired
    AuthService authService;

    @Autowired
    PeliculaRepository peliculaRepo;

    @Autowired
    GeneroRepository generoRepo;

    @Autowired
    GeneroService generoService;

    public void validateID(Long id) {
        if (!peliculaRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra película con id  " + id);
        }
    }

    public void validatePelicula(PeliculaEntity peliculaEntity) {
        ValidationHelper.validateStringLength(peliculaEntity.getTitulo(), 1, 45, "Título no válido");
        ValidationHelper.validateInt(peliculaEntity.getAño(),1950,2025, "Año no válido");
        ValidationHelper.validateInt(peliculaEntity.getDuracion(), 30, 230, "Duración no válida");
        ValidationHelper.validateStringLength(peliculaEntity.getDirector(), 3, 45, "Nombre no válido");
        ValidationHelper.validateFechaBaja(peliculaEntity.getFechaAlta(), peliculaEntity.getFechaBaja()); //Valida que la fecha de baja NO sea inferior a la de alta
        generoService.validate(peliculaEntity.getGenero().getId());
    }

    //CREATE Method (C)
    public Long create(PeliculaEntity newPelicula) {
        authService.onlyAdmins();
        validatePelicula(newPelicula);

        newPelicula.setId(0L);
        return peliculaRepo.save(newPelicula).getId();
    }

    //GET Method (R)
    public PeliculaEntity get(Long id) {
        validateID(id);
        return peliculaRepo.findById(id).get();
    }

    //UPDATE Method (U)
    @Transactional
    public Long update(PeliculaEntity updatedPelicula) {
        validateID(updatedPelicula.getId());
        authService.onlyAdmins();
        validatePelicula(updatedPelicula);

        PeliculaEntity actualPelicula = peliculaRepo.findById(updatedPelicula.getId()).get();
        actualPelicula.setTitulo(updatedPelicula.getTitulo());
        actualPelicula.setAño(updatedPelicula.getAño());
        actualPelicula.setDuracion(updatedPelicula.getDuracion());
        actualPelicula.setDirector(updatedPelicula.getDirector());
        actualPelicula.setFechaAlta(updatedPelicula.getFechaAlta());
        actualPelicula.setFechaBaja(updatedPelicula.getFechaBaja());
        actualPelicula.setVersionNormal(updatedPelicula.isVersionNormal());
        actualPelicula.setVersionEspecial(updatedPelicula.isVersionEspecial());
        actualPelicula.setGenero(generoService.get(updatedPelicula.getGenero().getId()));

        return peliculaRepo.save(actualPelicula).getId();
    }

    //DELETE Method (D)
    public Long delete(Long id) {
        authService.onlyAdmins();
        validateID(id);
        peliculaRepo.deleteById(id);

        if(peliculaRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro "+id);
        } else {
            return id;
        }
    }

    //COUNT Method
    public Long count() {
        //authService.onlyAdmins();
        return peliculaRepo.count();
    }

    //GETPAGE Method
    public Page<PeliculaEntity> getPage(Pageable pageable, String filter, Long id_genero) {
        
        if( filter.isBlank() && !id_genero.equals(null) ) {
            return peliculaRepo.findByGeneroId(id_genero, pageable);
        }
        else if( !filter.isBlank() && id_genero.equals(null) ) {
            return peliculaRepo.findByTituloIgnoreCaseContainingOrDirectorIgnoreCaseContaining(filter, filter, pageable);
        }
        else if( !filter.isBlank() && !id_genero.equals(null) ) {
            return peliculaRepo.findByTituloIgnoreCaseContainingOrDirectorIgnoreCaseContainingAndGeneroId(filter, filter, id_genero, pageable);
        }
        else {
            return peliculaRepo.findAll(pageable);
        }
    }
    
}
