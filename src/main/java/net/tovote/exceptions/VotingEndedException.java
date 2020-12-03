package net.tovote.exceptions;

public class VotingEndedException extends Exception{
    public VotingEndedException(){
        super("Voting has already ended!");
    }
}
