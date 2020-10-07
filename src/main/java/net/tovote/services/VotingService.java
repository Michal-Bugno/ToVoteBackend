package net.tovote.services;

import net.tovote.entities.Voting;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;

import java.util.List;

public interface VotingService {

    List<Voting> getAllForUsername(String username) throws UserNotFoundException;
    Voting getById(long id) throws VotingNotFoundException;
    void add(Voting voting);
    Voting deleteById(long id) throws VotingNotFoundException;
}
