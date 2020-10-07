package net.tovote.exceptions;

public class UsernameExistsException  extends UserException{
    public UsernameExistsException(String username){
        super("Username " + username + " already exists!");
    }
}
