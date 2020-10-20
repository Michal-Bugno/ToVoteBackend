package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.entities.Voting;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;

import java.util.List;

public interface GroupService {
    List<Group> getAllForUsername(String username) throws UserNotFoundException;
    boolean checkMembership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException;
}
