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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.cineServer.entity.SesionEntity;
import net.ausiasmarch.cineServer.service.SesionService;

@RestController
@RequestMapping("/sesion")
public class SesionControl {

    @Autowired
    SesionService sesionService;

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(sesionService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<SesionEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(value = "sala", required = false) Long id_sala,
        @RequestParam(value = "pelicula", required = false) Long id_pelicula,
        @RequestParam(value = "tarifa", required = false) Long id_tarifa
        ) {
        return new ResponseEntity<Page<SesionEntity>>(sesionService.getPage(pageable, id_sala, id_pelicula, id_tarifa), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<SesionEntity>(sesionService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody SesionEntity newSesionEntity) {
        return new ResponseEntity<Long>(sesionService.create(newSesionEntity), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody SesionEntity sesionEntity) {
        return new ResponseEntity<Long>(sesionService.update(sesionEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(sesionService.delete(id), HttpStatus.OK);
    }
    
}
