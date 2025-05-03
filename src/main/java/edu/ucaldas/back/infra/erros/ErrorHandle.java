package edu.ucaldas.back.infra.erros;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.ucaldas.back.infra.exception.DataNotFound;
import edu.ucaldas.back.infra.exception.EntityAlredyExists;
import edu.ucaldas.back.infra.exception.ErrorToken;
import edu.ucaldas.back.infra.exception.MissingData;
import edu.ucaldas.back.infra.exception.MissingToken;
import edu.ucaldas.back.infra.exception.SaveFileError;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandle {
    @ExceptionHandler({ EntityNotFoundException.class, DataNotFound.class })
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ ErrorToken.class, MissingToken.class })
    public ResponseEntity<String> handleErrorToken(ErrorToken e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({ MissingData.class, EntityAlredyExists.class, IllegalArgumentException.class })
    public ResponseEntity<String> handleMissingData(MissingData e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({ SaveFileError.class, IOException.class })
    public ResponseEntity<String> handleSaveFileError(SaveFileError e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
