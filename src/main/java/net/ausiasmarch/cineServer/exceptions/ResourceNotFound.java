package net.ausiasmarch.cineServer.exceptions;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String msg) {
        super("Error: " + msg);
    }
}
