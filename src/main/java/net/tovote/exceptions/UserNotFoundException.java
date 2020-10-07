package net.tovote.exceptions;

public class UserNotFoundException extends UserException{
    public UserNotFoundException(String username){
        super("User" + username + " not found!");
    }
}
