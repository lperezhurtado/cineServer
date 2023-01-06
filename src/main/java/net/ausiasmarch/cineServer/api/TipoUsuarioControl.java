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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.cineServer.entity.TipoUsuarioEntity;
import net.ausiasmarch.cineServer.service.TipoUsuarioService;

@RestController
@RequestMapping("/tipousuario")
public class TipoUsuarioControl {
    
    @Autowired
    TipoUsuarioService tipoUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioEntity> get(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<TipoUsuarioEntity>(tipoUsuarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TipoUsuarioEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable
        ) {
        return new ResponseEntity<Page<TipoUsuarioEntity>>(tipoUsuarioService.getPage(oPageable), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(tipoUsuarioService.count(), HttpStatus.OK);
    }

    /*@PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody TipoUsuarioEntity newTipoUsuarioEntity) {
        return new ResponseEntity<Long>(tipoUsuarioService.create(newTipoUsuarioEntity), HttpStatus.OK);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(tipoUsuarioService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody TipoUsuarioEntity tipoUsuarioEntity) {
        return new ResponseEntity<Long>(tipoUsuarioService.update(tipoUsuarioEntity), HttpStatus.OK);
    }
}
