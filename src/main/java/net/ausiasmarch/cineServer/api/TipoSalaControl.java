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

import net.ausiasmarch.cineServer.entity.TipoSalaEntity;
import net.ausiasmarch.cineServer.service.TipoSalaService;

@RestController
@RequestMapping("/tiposala")
public class TipoSalaControl {

    @Autowired
    TipoSalaService tipoSalaService;

    //GET PAGE
    @GetMapping("")
    public ResponseEntity<Page<TipoSalaEntity>> getPage (
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
        ) {
        return new ResponseEntity<Page<TipoSalaEntity>>(tipoSalaService.getPage(pageable, filter), HttpStatus.OK);
    }
    //COUNT
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(tipoSalaService.count(), HttpStatus.OK);
    }
    //CREATE (C)
    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody TipoSalaEntity newTipoSalaEntity) {
        return new ResponseEntity<Long>(tipoSalaService.create(newTipoSalaEntity), HttpStatus.OK);
    }
    //GET (R)
    @GetMapping("/{id}")
    public ResponseEntity<TipoSalaEntity> get(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<TipoSalaEntity>(tipoSalaService.get(id), HttpStatus.OK);
    }
    //UPDATE (U)
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody TipoSalaEntity tipoSalaEntity) {
        return new ResponseEntity<Long>(tipoSalaService.update(tipoSalaEntity), HttpStatus.OK);
    }
    //DELETE (D)
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(tipoSalaService.delete(id), HttpStatus.OK);
    }
    
}
