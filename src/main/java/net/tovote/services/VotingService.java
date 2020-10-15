package net.tovote.services;

import net.tovote.entities.Vote;
import net.tovote.entities.Voting;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;

import java.util.List;

public interface VotingService {

    List<Voting> getAllForUsername(String username) throws UserNotFoundException;
    Voting getById(long id) throws VotingNotFoundException;
    void add(Voting voting);
    List<Vote> getAllVotes(long votingId) throws VotingNotFoundException;
    void submitVote(long votingId, Vote vote) throws VotingNotFoundException;
    void update(Voting voting) throws VotingNotFoundException;
    Voting deleteById(long id) throws VotingNotFoundException;
}
