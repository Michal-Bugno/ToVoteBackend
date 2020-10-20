package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.repositories.GroupRepository;
import net.tovote.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImplementation implements GroupService{

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    @Autowired
    public GroupServiceImplementation(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAllForUsername(String username) throws UserNotFoundException {
        if(!userRepository.existsById(username))
            throw new UserNotFoundException(username);
        List<Group> groups = userRepository.findById(username).get().getGroups();
        return groups;
    }

    @Override
    public boolean checkMembership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException {
        if(!groupRepository.existsById(groupId))
            throw new GroupNotFoundException("There is no group with given ID!");
        List<User> users = groupRepository.findById(groupId).get().getUsers();
        for(User u : users){
            if(u.getUsername().equals(username))
                return true;
        }
        return false;
    }
}
