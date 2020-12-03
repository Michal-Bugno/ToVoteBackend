package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.entities.Voting;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;
import net.tovote.repositories.GroupRepository;
import net.tovote.repositories.UserRepository;
import net.tovote.repositories.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class GroupServiceImplementation implements GroupService{

    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private VotingRepository votingRepository;

    @Autowired
    public GroupServiceImplementation(GroupRepository groupRepository, UserRepository userRepository, VotingRepository votingRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.votingRepository = votingRepository;
    }

    @Override
    public void add(Group group, String ownerUsername) throws UserNotFoundException{
        Optional<User> user = userRepository.findById(ownerUsername);
        if(user.isEmpty())
            throw new UserNotFoundException(ownerUsername);
        group.setOwner(user.get());
        groupRepository.save(group);
    }

    @Override
    public void update(Group group) throws GroupNotFoundException{
        Optional<Group> groupToUpdate = groupRepository.findById(group.getGroupId());
        if(groupToUpdate.isEmpty())
            throw new GroupNotFoundException("There is no group with given ID!");
        Group updatedGroup = groupToUpdate.get();
        updatedGroup.setName(group.getName());
        updatedGroup.setDescription(group.getDescription());
        groupRepository.save(updatedGroup);
    }

    @Override
    public Group delete(long groupId) throws GroupNotFoundException {
        Optional<Group> groupToDelete = groupRepository.findById(groupId);
        if(groupToDelete.isEmpty())
            throw new GroupNotFoundException("There is no group with given ID!");
        for(User u : groupToDelete.get().getUsers())
            groupToDelete.get().removeUser(u);
        groupRepository.delete(groupToDelete.get());
        return groupToDelete.get();
    }

    @Override
    public Group getById(long groupId) throws GroupNotFoundException{
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty())
            throw new GroupNotFoundException("No group with given ID!");
        return group.get();
    }

    @Override
    public Set<Group> getAllExceptOwnedForUsername(String username) throws UserNotFoundException {
        if(!userRepository.existsById(username))
            throw new UserNotFoundException(username);
        Set<Group> groups = userRepository.findById(username).get().getGroups();
        return groups;
    }

    @Override
    public Set<Group> getOwnedForUsername(String username) throws UserNotFoundException {
        if(!userRepository.existsById(username))
            throw new UserNotFoundException(username);
        Set<Group> ownedGroups = groupRepository.findAllOwnedBy(username);
        return ownedGroups;
    }


    @Override
    public boolean checkMembership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException {
        if(!groupRepository.existsById(groupId))
            throw new GroupNotFoundException("There is no group with given ID!");
        if(!userRepository.existsById(username))
            throw new UserNotFoundException(username);
        Set<User> users = groupRepository.findById(groupId).get().getUsers();
        for(User u : users){
            if(u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    @Override
    public boolean checkOwnership(String username, long groupId) throws UserNotFoundException, GroupNotFoundException {
        if(!groupRepository.existsById(groupId))
            throw new GroupNotFoundException("There is no group with given ID!");
        if(!userRepository.existsById(username))
            throw new UserNotFoundException(username);
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.get().getOwner().getUsername().equals(username))
            return true;
        return false;
    }

    @Override
    public User addUser(String username, long groupId) throws UserNotFoundException, GroupNotFoundException {
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty())
            throw new GroupNotFoundException("No group with given ID!");
        Optional<User> user = userRepository.findById(username);
        if(user.isEmpty())
            throw new UserNotFoundException(username);
        group.get().addUser(user.get());
        groupRepository.save(group.get());
        User returnedUser = user.get();
        returnedUser.setPassword("---");
        return returnedUser;
    }

    @Override
    public User deleteUser(String username, long groupId) throws GroupNotFoundException, UserNotFoundException {
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty())
            throw new GroupNotFoundException("No group with given ID!");
        Optional<User> user = userRepository.findById(username);
        if(user.isEmpty())
            throw new UserNotFoundException(username);
        group.get().removeUser(user.get());
        User returnedUser = user.get();
        returnedUser.setPassword("---");
        return returnedUser;
    }

    @Override
    public Set<Group> getAllForVoting(long votingId) throws VotingNotFoundException {
        var voting = votingRepository.findById(votingId);
        if(voting.isEmpty())
            throw new VotingNotFoundException("No voting with given id!");
        return voting.get().getGroups();
    }
}
