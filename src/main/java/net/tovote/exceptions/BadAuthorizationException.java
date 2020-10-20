package net.tovote.exceptions;

public class BadAuthorizationException extends UserException{
    public BadAuthorizationException(String message){
        super(message);
    }
}
