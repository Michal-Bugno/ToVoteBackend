package net.tovote.exceptions;

public class UserNotAuthorizedToVoteException extends VotingException{
    public UserNotAuthorizedToVoteException(String username){
        super(username + " is not authorized to vote in this voting!");
    }
}
