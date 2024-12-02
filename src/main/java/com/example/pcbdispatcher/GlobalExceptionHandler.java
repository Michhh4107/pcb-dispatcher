package com.example.pcbdispatcher;

import com.example.pcbdispatcher.dto.MessageResponseDto;
import com.example.pcbdispatcher.exception.ActionNotAllowedException;
import com.example.pcbdispatcher.exception.BoardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseBody
    public MessageResponseDto handleBoardNotFoundException(BoardNotFoundException ex) {
        return MessageResponseDto.builder().message(ex.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ActionNotAllowedException.class)
    @ResponseBody
    public MessageResponseDto handleActionNotAllowedException(ActionNotAllowedException ex) {
        return MessageResponseDto.builder().message(ex.getMessage()).build();
    }
}
