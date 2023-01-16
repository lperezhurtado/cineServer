package net.ausiasmarch.cineServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServer.entity.PeliculaEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
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

    public void validatePeli(PeliculaEntity peliculaEntity) {
        ValidationHelper.validateStringLength(peliculaEntity.getTitulo(), 1, 45, "Título no válido");
        ValidationHelper.validateInt(peliculaEntity.getAño(),1950,2025, "Año no válido");
        ValidationHelper.validateInt(peliculaEntity.getDuracion(), 30, 230, "Duración no válida");
        ValidationHelper.validateStringLength(peliculaEntity.getDirector(), 3, 45, "Nombre no válido");
        ValidationHelper.validateFechaBaja(peliculaEntity.getFechaAlta(), peliculaEntity.getFechaBaja()); //Valida que la fecha de baja NO sea inferior a la de alta
        generoService.validate(peliculaEntity.getGenero().getId());
    }
    //GET Method
    public PeliculaEntity get(Long id) {
        validateID(id);
        return peliculaRepo.findById(id).get();
    }

    public Long create(PeliculaEntity newPelicula) {
        authService.onlyAdmins();
        validatePeli(newPelicula);

        newPelicula.setId(0L);
        return peliculaRepo.save(newPelicula).getId();
    }
    
}
