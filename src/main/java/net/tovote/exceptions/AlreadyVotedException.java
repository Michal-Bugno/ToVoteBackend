package net.tovote.exceptions;

public class AlreadyVotedException extends VotingException {
    public AlreadyVotedException(String username){
        super(username +" has already voted!");
    }
}
