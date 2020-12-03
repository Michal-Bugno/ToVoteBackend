package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.Vote;
import net.tovote.entities.Voting;
import net.tovote.exceptions.AlreadyVotedException;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;

import java.util.List;
import java.util.Set;

public interface VotingService {

    List<Voting> getAllForUsername(String username) throws UserNotFoundException;
    Voting getById(long id) throws VotingNotFoundException;
    void add(Voting voting, String Username) throws UserNotFoundException;
    Group addGroup(long votingId, long groupId) throws VotingNotFoundException, GroupNotFoundException;
    Group deleteGroup(long votingId, long groupId) throws VotingNotFoundException, GroupNotFoundException;
    boolean checkOwnership(String Username, long votingId) throws UserNotFoundException, VotingNotFoundException;
    Set<Vote> getAllVotes(long votingId) throws VotingNotFoundException;
    void submitVote(long votingId, Vote vote) throws VotingNotFoundException, AlreadyVotedException;
    void update(Voting voting) throws VotingNotFoundException;
    Voting deleteById(long id) throws VotingNotFoundException;
    Set<Voting> getAllAuthorizedForUsername(String username) throws UserNotFoundException;
}
