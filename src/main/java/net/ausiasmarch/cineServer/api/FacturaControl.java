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

import net.ausiasmarch.cineServer.entity.FacturaEntity;
import net.ausiasmarch.cineServer.service.FacturaService;

@RestController
@RequestMapping("/factura")
public class FacturaControl {
    
    @Autowired
    FacturaService facturaService;

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(facturaService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<FacturaEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(value = "usuario", required = false) Long id_usuario
        ) {
        return new ResponseEntity<Page<FacturaEntity>>(facturaService.getPage(pageable, id_usuario), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<FacturaEntity>(facturaService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody FacturaEntity newFacturaEntity) {
        return new ResponseEntity<Long>(facturaService.create(newFacturaEntity), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody FacturaEntity facturaEntity) {
        return new ResponseEntity<Long>(facturaService.update(facturaEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(facturaService.delete(id), HttpStatus.OK);
    }
}
