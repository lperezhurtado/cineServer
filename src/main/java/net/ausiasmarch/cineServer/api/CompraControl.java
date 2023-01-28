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

import net.ausiasmarch.cineServer.entity.CompraEntity;
import net.ausiasmarch.cineServer.service.CompraService;

@RestController
@RequestMapping("/compra")
public class CompraControl {

    @Autowired
    CompraService compraService;

    @GetMapping("/{id}")
    public ResponseEntity<CompraEntity> get(@PathVariable(value="id") Long id) {
        return new ResponseEntity<>(compraService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody CompraEntity newCompra) {
        return new ResponseEntity<Long>(compraService.create(newCompra), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody CompraEntity updatedCompra) {
        return new ResponseEntity<Long>(compraService.update(updatedCompra), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(compraService.delete(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<CompraEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(name = "factura", required = false) Long id_factura,
        @RequestParam(value = "entrada", required = false) Long id_entrada
    ) {
        return new ResponseEntity<Page<CompraEntity>>(compraService.getPage(pageable, id_factura), HttpStatus.OK);
    }
    
}
