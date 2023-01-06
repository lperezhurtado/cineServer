package net.ausiasmarch.cineServer.exceptions;

import java.util.Date;
import net.ausiasmarch.cineServer.bean.ErrorResponseBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {
    
    @ExceptionHandler(UnauthorizationException.class)
    public ResponseEntity<?> UnauthorizedException(UnauthorizationException ex, WebRequest request) {
        ErrorResponseBean errorDetails
                = new ErrorResponseBean(new Date(), HttpStatus.UNAUTHORIZED.name(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> ResourceNotFoundException(ResourceNotFound ex, WebRequest request) {
        ErrorResponseBean errorDetails
                = new ErrorResponseBean(new Date(), HttpStatus.NOT_FOUND.name(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /*@ExceptionHandler(ResourceNotModifiedException.class)
    public ResponseEntity<?> ResourceNotModifiedException(ResourceNotModifiedException ex, WebRequest request) {
        ErrorResponseBean errorDetails
                = new ErrorResponseBean(new Date(), HttpStatus.NOT_MODIFIED.name(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_MODIFIED);
    }*/

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> ValidationException(ValidationException ex, WebRequest request) {
        ErrorResponseBean errorDetails
                = new ErrorResponseBean(new Date(), HttpStatus.NOT_ACCEPTABLE.name(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> AppExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponseBean errorDetails
                = new ErrorResponseBean(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

