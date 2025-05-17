package edu.ucaldas.back.infra.erros;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.ucaldas.back.DTO.ErrorDTO;
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
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(e.getMessage(), "404"));
    }

    @ExceptionHandler({ ErrorToken.class, MissingToken.class })
    public ResponseEntity<ErrorDTO> handleErrorToken(ErrorToken e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(e.getMessage(), "401"));
    }

    @ExceptionHandler({ MissingData.class, EntityAlredyExists.class, IllegalArgumentException.class })
    public ResponseEntity<ErrorDTO> handleMissingData(MissingData e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage(), "400"));
    }

    @ExceptionHandler({ SaveFileError.class, IOException.class })
    public ResponseEntity<ErrorDTO> handleSaveFileError(SaveFileError e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage(), "500"));
    }

}