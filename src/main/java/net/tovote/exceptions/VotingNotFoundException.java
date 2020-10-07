package net.tovote.exceptions;

public class VotingNotFoundException extends VotingException{
    public VotingNotFoundException(String message) {
        super("Voting has not been found!");
    }
}
