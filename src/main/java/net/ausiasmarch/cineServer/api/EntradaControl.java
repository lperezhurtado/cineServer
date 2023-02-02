package net.ausiasmarch.cineServer.api;

import java.util.List;

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

import net.ausiasmarch.cineServer.entity.EntradaEntity;
import net.ausiasmarch.cineServer.service.EntradaService;

@RestController
@RequestMapping("/entrada")
public class EntradaControl {

    @Autowired
    EntradaService entradaService;

    @GetMapping("/{id}")
    public ResponseEntity<EntradaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(entradaService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody EntradaEntity newEntrada) {
        return new ResponseEntity<Long>(entradaService.create(newEntrada), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody EntradaEntity updatedEntrada) {
        return new ResponseEntity<Long>(entradaService.update(updatedEntrada), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(entradaService.delete(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<EntradaEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(value = "sesion", required = false) Long id_sesion
        ) {
        return new ResponseEntity<Page<EntradaEntity>>(entradaService.getPage(pageable, id_sesion), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<EntradaEntity>> getList(
        //@ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(value = "sesion", required = false) Long id_sesion
        ) {
        return new ResponseEntity<List<EntradaEntity>>(entradaService.getList(id_sesion), HttpStatus.OK);
    }
    
}
