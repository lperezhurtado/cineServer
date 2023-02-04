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

import net.ausiasmarch.cineServer.entity.TarifaEntity;
import net.ausiasmarch.cineServer.service.TarifaService;

@RestController
@RequestMapping("/tarifa")
public class TarifaControl {
    
    @Autowired
    TarifaService tarifaService;

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(tarifaService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TarifaEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
        ) {
        return new ResponseEntity<Page<TarifaEntity>>(tarifaService.getPage(pageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TarifaEntity>(tarifaService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody TarifaEntity newTarifaEntity) {
        return new ResponseEntity<Long>(tarifaService.create(newTarifaEntity), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody TarifaEntity tarifaEntity) {
        return new ResponseEntity<Long>(tarifaService.update(tarifaEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(tarifaService.delete(id), HttpStatus.OK);
    }
}
