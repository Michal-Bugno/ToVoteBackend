package net.tovote.exceptions;

public class AlreadyAddedException extends Exception{

    public AlreadyAddedException(String itemName){
        super(itemName+" has already been added!");
    }
}
