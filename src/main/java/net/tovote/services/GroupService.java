package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.entities.Voting;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;

import java.util.List;

public interface GroupService {
    void add(Group group, String ownerUsername) throws UserNotFoundException;
    void update(Group group) throws GroupNotFoundException;
    List<Group> getAllForUsername(String username) throws UserNotFoundException;
    boolean checkMembership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException;
    boolean checkOwnership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException;
}
