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

import net.ausiasmarch.cineServer.entity.SalaEntity;
import net.ausiasmarch.cineServer.service.SalaService;

@RestController
@RequestMapping("/sala")
public class SalaControl {

    @Autowired
    SalaService salaService;

    @GetMapping("/{id}")
    public ResponseEntity<SalaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(salaService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody SalaEntity newSala) {
        return new ResponseEntity<Long>(salaService.create(newSala), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody SalaEntity updatedSala) {
        return new ResponseEntity<Long>(salaService.update(updatedSala), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(salaService.delete(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<SalaEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(value = "tiposala", required = false) Long id_tiposala
        ) {
        return new ResponseEntity<Page<SalaEntity>>(salaService.getPage(pageable, id_tiposala), HttpStatus.OK);
    }
}
