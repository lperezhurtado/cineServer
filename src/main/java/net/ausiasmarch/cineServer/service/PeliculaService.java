package net.ausiasmarch.cineServer.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import org.springframework.util.StringUtils;

import net.ausiasmarch.cineServer.entity.PeliculaEntity;
import net.ausiasmarch.cineServer.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServer.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServer.helper.FileHelper;
import net.ausiasmarch.cineServer.helper.RandomHelper;
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
    /*public Long create(PeliculaEntity newPelicula) {
        authService.onlyAdmins();
        validatePelicula(newPelicula);

        newPelicula.setId(0L);
        return peliculaRepo.save(newPelicula).getId();
    }*/

    //CREATE CON IMAGEN
    public Long create(String newPelicula, MultipartFile multipartfile) {
        authService.onlyAdmins();

        String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename()); //Obtenemos el nombre del fichero
        String uploadDir = "src/images/peliculas"; //Establecemos el directorio donde se subiran nuestros ficheros  

        String sufix = RandomHelper.dateLong().toString();
        String uniqueFileName = sufix+"-"+fileName;

        Gson gson = new Gson();
        PeliculaEntity pelicula = gson.fromJson(newPelicula, PeliculaEntity.class); //convierte el String a UsuarioEntity

        validatePelicula(pelicula);
        pelicula.setId(0L);
        pelicula.setImagen(uniqueFileName); //Se guarda la imagen en newPelicula
        
        FileHelper.saveFile(uploadDir, uniqueFileName, multipartfile); //Guarda la imagen en la carpeta images

        return peliculaRepo.save(pelicula).getId();
    }

    //GET Method (R)
    public PeliculaEntity get(Long id) {
        validateID(id);
        return peliculaRepo.findById(id).get();
    }

    //UPDATE Method (U)
    /*@Transactional
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
    }*/

    //UPDATE CON IMAGEN (U)
    @Transactional
    public Long update(String updatedPelicula, MultipartFile multipartfile ) {

        Gson gson = new Gson();
        PeliculaEntity pelicula = gson.fromJson(updatedPelicula, PeliculaEntity.class);
        validatePelicula(pelicula);

        if(!multipartfile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename()); //Obtenemos el nombre del fichero
            String uploadDir = "src/images/peliculas";

            String sufix = RandomHelper.dateLong().toString();
            String uniqueFileName = sufix+"-"+fileName;
            pelicula.setImagen(uniqueFileName);

            PeliculaEntity actualPelicula = peliculaRepo.findById(pelicula.getId()).get();
            String actualImage = actualPelicula.getImagen();
            
            FileHelper.saveFile(uploadDir, uniqueFileName, multipartfile); //Guarda la nueva imagen
            FileHelper.deleteImage(uploadDir, actualImage); //Borra la imagen anterior
        }

        return peliculaRepo.save(pelicula).getId();
    } 

    //DELETE Method (D)
    public Long delete(Long id) {
        authService.onlyAdmins();
        validateID(id);
        PeliculaEntity deletedPelicula = peliculaRepo.getReferenceById(id);
        String imagen = deletedPelicula.getImagen();
        String uploadDir = "src/images/peliculas";
        FileHelper.deleteImage(uploadDir, imagen);
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
        
        if (filter == null || filter.length() == 0) {
            if (id_genero == null) {
                return peliculaRepo.findAll(pageable);
            } else {
                return peliculaRepo.findByGeneroId(id_genero, pageable);
            }
        } else {
            if (id_genero == null) {
                return peliculaRepo.findByTituloIgnoreCaseContainingOrDirectorIgnoreCaseContaining(filter, filter, pageable);
            } else {
                return peliculaRepo.findByTituloIgnoreCaseContainingOrDirectorIgnoreCaseContainingAndGeneroId(filter, filter, id_genero, pageable);
            }
            
        }

        //REVISAR ESTRUCTURA DE GETPAGE

        /*if( filter.isBlank() && !id_genero.equals(null) ) {
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
        }*/
    }
    
}
