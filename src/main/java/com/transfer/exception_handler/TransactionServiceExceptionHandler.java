package com.transfer.exception_handler;

import com.transfer.application_service.TransferApplicationService;
import com.transfer.error.ErrorDto;
import com.transfer.exception.AccountNotFoundException;
import com.transfer.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TransactionServiceExceptionHandler {

    public static Logger log = LoggerFactory.getLogger(TransferApplicationService.class);

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleInvalidDataException(InvalidDataException invalidDataException) {
        ErrorDto errorDto = new ErrorDto.Builder()
                .setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .setDescription(invalidDataException.getMessage())
                .setTitle(invalidDataException.getMessage())
                .build();
        log.error("Error in operation :{}",invalidDataException.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException) {
        ErrorDto errorDto = new ErrorDto.Builder()
                .setStatus(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .setDescription(accountNotFoundException.getMessage())
                .setTitle(accountNotFoundException.getMessage())
                .build();
        log.error("Error in operation :{}",accountNotFoundException.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        ErrorDto errorDto = new ErrorDto.Builder()
                .setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .setDescription(exception.getMessage())
                .setTitle(exception.getMessage())
                .build();
        log.error("Error in operation :{}",exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
