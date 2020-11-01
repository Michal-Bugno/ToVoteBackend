package net.tovote.exceptions;

import net.tovote.exceptions.UserException;

public class EmailInUseException extends UserException {
    public EmailInUseException(){
        super("Provided email is already in use!");
    }
}
