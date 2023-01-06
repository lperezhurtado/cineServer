package net.ausiasmarch.cineServer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.cineServer.bean.UsuarioBean;
import net.ausiasmarch.cineServer.entity.UsuarioEntity;
import net.ausiasmarch.cineServer.service.AuthService;

@RestController
@RequestMapping("/login")
public class LoginControl {
    
    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<UsuarioEntity> login(@RequestBody UsuarioBean usuarioBean) {
        return new ResponseEntity<UsuarioEntity>(authService.login(usuarioBean), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<?> logout() {
        authService.logout();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<UsuarioEntity> check() {
        return new ResponseEntity<UsuarioEntity>(authService.check(), HttpStatus.OK);
    }
}
