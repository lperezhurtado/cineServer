package net.ausiasmarch.cineServer.service;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import net.ausiasmarch.cineServer.bean.UsuarioBean;
import net.ausiasmarch.cineServer.entity.UsuarioEntity;
import net.ausiasmarch.cineServer.exceptions.UnauthorizationException;
import net.ausiasmarch.cineServer.repository.UsuarioRepository;

@Service
public class AuthService {
    
    @Autowired
    HttpSession httpSession;

    @Autowired
    UsuarioRepository usuarioRepo;

    //login
    public UsuarioEntity login(@RequestBody UsuarioBean usuarioBean) {

        if (usuarioBean.getPassword() != null) {
            UsuarioEntity usuarioLogin = usuarioRepo.findByLoginAndPassword(usuarioBean.getLogin(), usuarioBean.getPassword());

            if (usuarioLogin != null) {
                httpSession.setAttribute("usuario", usuarioLogin);
                return usuarioLogin;
            }
            else{
                throw new UnauthorizationException("Datos incorrectos");
            }   
        }
        else{
            throw new UnauthorizationException("Contraseña errónea");
        }
    }
    //logout
    public void logout() {
        httpSession.invalidate();
    }
    //check
    public UsuarioEntity check() {
        UsuarioEntity usuarioLogged = (UsuarioEntity) httpSession.getAttribute("usuario");
        if (usuarioLogged != null) {
            return usuarioLogged;
        }
        else{
            throw new UnauthorizationException("No hay sesiones");
        }
    }

    public void onlyAdmins() {
        UsuarioEntity usuarioLogged = (UsuarioEntity) httpSession.getAttribute("usuario");
        if (usuarioLogged == null || usuarioLogged.getTipousuario().getId() != 1) {
            throw new UnauthorizationException("Función sólo para administradores");
        }
    }

    public void onlyAdminsOrNewUser() { //MIRAR PARA QUE UN USUARIO VISITANTE SE PUEDA CREAR UN USUARIO CON TIPOUSUARIO 2
        UsuarioEntity usuarioLogged = (UsuarioEntity) httpSession.getAttribute("usuario");
        if(usuarioLogged != null && usuarioLogged.getTipousuario().getId() != 1) {
            throw new UnauthorizationException("Accion sólo para administradores y nuevos usuarios");
        }
    }

    public void onlyUsers() {
        UsuarioEntity usuarioLogged = (UsuarioEntity) httpSession.getAttribute("usuario");
        if (usuarioLogged == null || usuarioLogged.getTipousuario().getId() != 2) {
            throw new UnauthorizationException("Función sólo para usuarios registrados");
        }
    }

    public void OnlyAdminsOrOwnUserData(Long id) {
        UsuarioEntity usuarioLogged = (UsuarioEntity) httpSession.getAttribute("usuario");

        if (usuarioLogged == null) {
            throw new UnauthorizationException("Sólo para administradores o usuarios");
        } 
        else if(usuarioLogged.getTipousuario().getId() == 2 && usuarioLogged.getId() != id){
            throw new UnauthorizationException("Sólo puedes consultar tus propios datos");
        }
    }

    public boolean isAdmin() {

        boolean isAdmin = true;
        UsuarioEntity usuarioLogged = (UsuarioEntity) httpSession.getAttribute("usuario");

        if(usuarioLogged != null && usuarioLogged.getTipousuario().getId().equals(1L)) {
            isAdmin = true;
        }
        else if(usuarioLogged != null && usuarioLogged.getTipousuario().getId().equals(2L)) {
            isAdmin = false;
        }
        return isAdmin;
    }
}