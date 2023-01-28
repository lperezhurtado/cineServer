package net.ausiasmarch.cineServer.api;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.ausiasmarch.cineServer.entity.PeliculaEntity;
import net.ausiasmarch.cineServer.service.PeliculaService;

@RestController
@RequestMapping("/pelicula")
public class PeliculaControl {

    @Autowired
    PeliculaService peliculaService;

    //COUNT
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(peliculaService.count(), HttpStatus.OK);
    }

    //GETPAGE
    @GetMapping("")
    public ResponseEntity<Page<PeliculaEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "genero", required = false) Long id_genero
        ) {
        return new ResponseEntity<Page<PeliculaEntity>>(peliculaService.getPage(pageable, filter, id_genero), HttpStatus.OK);
    }

    //CREATE (C)
    /*@PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody PeliculaEntity newPelicula) {
        return new ResponseEntity<Long>(peliculaService.create(newPelicula), HttpStatus.OK);
    }*/

    //CREATE CON IMAGEN
    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestParam(name="pelicula") String pelicula, @RequestParam(name="fichero", required = false) MultipartFile multipartfile ) {
        return new ResponseEntity<Long>(peliculaService.create(pelicula, multipartfile), HttpStatus.OK);
    }

    //GET (R)
    @GetMapping("/{id}")
    public ResponseEntity<PeliculaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<PeliculaEntity>(peliculaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable(value = "filename") String filename) {
        return new ResponseEntity<byte[]>(peliculaService.getImage(filename), HttpStatus.OK);
    }

    //UPDATE (U)
    /*@PutMapping("")
    public ResponseEntity<Long> update(@RequestBody PeliculaEntity updatedPelicula) {
        return new ResponseEntity<Long>(peliculaService.update(updatedPelicula), HttpStatus.OK);
    }*/

    //UPDATE CON IMAGEN (U)
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestParam(name = "pelicula") String updatedPelicula, @RequestParam(name="fichero", required = false) MultipartFile multipartfile) {
        return new ResponseEntity<Long>(peliculaService.update(updatedPelicula, multipartfile), HttpStatus.OK);
    }

    //DELETE (D)
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(peliculaService.delete(id), HttpStatus.OK);
    }
    
}
