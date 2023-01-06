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

import net.ausiasmarch.cineServer.entity.UsuarioEntity;
import net.ausiasmarch.cineServer.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioControl {
    
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(usuarioService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> create(@RequestBody UsuarioEntity newUsuario) {
        return new ResponseEntity<Long>(usuarioService.create(newUsuario), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody UsuarioEntity updatedUsuario) {
        return new ResponseEntity<Long>(usuarioService.update(updatedUsuario), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(usuarioService.delete(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<UsuarioEntity>> getPage(
        @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
        @RequestParam(name = "filter", required = false) String strFilter,
        @RequestParam(value = "tipousuario", required = false) Long id_tipousuario
        ) {
        return new ResponseEntity<Page<UsuarioEntity>>(usuarioService.getPage(oPageable, strFilter, id_tipousuario), HttpStatus.OK);
    }

    //GENERA USUARIOS
    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(usuarioService.generateSome(amount), HttpStatus.OK);
    }
}
