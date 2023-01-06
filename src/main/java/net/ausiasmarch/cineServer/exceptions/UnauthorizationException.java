package net.ausiasmarch.cineServer.exceptions;

public class UnauthorizationException extends RuntimeException {
    
    public UnauthorizationException(String msg) {
        super("Acceso no permitido: " + msg);
    }
}
