package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.entities.Voting;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;

import java.util.List;
import java.util.Set;

public interface GroupService {
    void add(Group group, String ownerUsername) throws UserNotFoundException;
    void update(Group group) throws GroupNotFoundException;
    Group delete(long groupId) throws GroupNotFoundException;
    Group getById(long groupId) throws GroupNotFoundException;
    Set<Group> getAllExceptOwnedForUsername(String username) throws UserNotFoundException;
    Set<Group> getOwnedForUsername(String username) throws UserNotFoundException;
    boolean checkMembership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException;
    boolean checkOwnership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException;
    User addUser(String username, long groupId) throws UserNotFoundException, GroupNotFoundException;
    User deleteUser(String username, long groupId) throws UserNotFoundException, GroupNotFoundException;
    Set<Group> getAllForVoting(long votingId) throws VotingNotFoundException;
}
