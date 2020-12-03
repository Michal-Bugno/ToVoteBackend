package net.tovote.exceptions;

public class VotingNotStartedException extends Exception{
    public VotingNotStartedException(){
        super("Voting has not started yet!");
    }
}
