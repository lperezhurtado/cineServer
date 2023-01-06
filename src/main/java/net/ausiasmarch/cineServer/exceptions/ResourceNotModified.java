package net.ausiasmarch.cineServer.exceptions;

public class ResourceNotModified extends RuntimeException{
    
    public ResourceNotModified(String msg) {
        super("ERROR: Resource not modified: " + msg);
    }
}
