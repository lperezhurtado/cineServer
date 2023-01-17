package net.ausiasmarch.cineServer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //CREATE (C)
    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody PeliculaEntity newPelicula) {
        return new ResponseEntity<Long>(peliculaService.create(newPelicula), HttpStatus.OK);
    }

    //GET (R)
    @GetMapping("/{id}")
    public ResponseEntity<PeliculaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<PeliculaEntity>(peliculaService.get(id), HttpStatus.OK);
    }

    //UPDATE (U)
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody PeliculaEntity updatedPelicula) {
        return new ResponseEntity<Long>(peliculaService.update(updatedPelicula), HttpStatus.OK);
    }

    //DELETE (D)
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(peliculaService.delete(id), HttpStatus.OK);
    }
    
}
