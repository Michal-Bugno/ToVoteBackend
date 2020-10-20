package net.tovote.services;

import net.tovote.entities.User;
import net.tovote.entities.Voting;
import net.tovote.exceptions.GroupNotFoundException;

import java.util.List;

public interface GroupService {
    List<User> getUsers(long groupId) throws GroupNotFoundException;
    List<Voting> getVotings(long groupId) throws GroupNotFoundException;

}
