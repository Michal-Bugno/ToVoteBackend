package net.tovote.controllers;

import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;
import net.tovote.responses.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException u){

    }
}
