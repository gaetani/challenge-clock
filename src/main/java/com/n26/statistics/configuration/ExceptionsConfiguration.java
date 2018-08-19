package com.n26.statistics.configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.n26.statistics.exception.TransactionOlderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ExceptionsConfiguration {


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String uuid = UUID.randomUUID().toString();
        //log.error(uuid, ex);
        final Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            return ResponseEntity.unprocessableEntity().build();
        } else if (cause instanceof MismatchedInputException) {
            return ResponseEntity.badRequest().build();
        } else if (cause instanceof JsonMappingException) {
            return ResponseEntity.unprocessableEntity().build();
        } else return ResponseEntity.badRequest().build();
    }


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public void handleAllException(MethodArgumentNotValidException ex) {
        String uuid = UUID.randomUUID().toString();
        //log.error(uuid, ex);
    }


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(NO_CONTENT)
    public void handleTransactionOlderExceptionException(TransactionOlderException ex) {
        String uuid = UUID.randomUUID().toString();
      // log.error(uuid, ex);
    }


}
