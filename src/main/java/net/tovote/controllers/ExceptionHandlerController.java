package net.tovote.controllers;

import net.tovote.exceptions.BadAuthorizationException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.UsernameExistsException;
import net.tovote.exceptions.VotingNotFoundException;
import net.tovote.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException u){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), u.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExistsException(UsernameExistsException u){
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), u.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(VotingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVotingNotFoundException(VotingNotFoundException v){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), v.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleBadAuthorizationException(BadAuthorizationException b){
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), b.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
