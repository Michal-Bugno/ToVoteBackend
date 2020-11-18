package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.exceptions.EmailInUseException;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.UsernameExistsException;
import net.tovote.repositories.GroupRepository;
import net.tovote.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService{


    private UserRepository userRepository;
    private GroupRepository groupRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, GroupRepository groupRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public User getByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if(!user.isPresent())
            throw new UserNotFoundException(username);
        return user.get();
    }

    @Override
    public void add(User user) throws UsernameExistsException, EmailInUseException {
        if(userRepository.existsById(user.getUsername()))
            throw new UsernameExistsException(user.getUsername());
        User exampleUser = new User();
        exampleUser.setEmail(user.getEmail());
        Example<User> example = Example.of(exampleUser);
        if(userRepository.exists(example))
            throw new EmailInUseException();
        userRepository.save(user);
    }

    @Override
    public void update(User user) throws UserNotFoundException{

        Optional<User> userToUpdate = userRepository.findById(user.getUsername());
        if(!userToUpdate.isPresent())
            throw new UserNotFoundException(user.getUsername());
        User updatedUser = userToUpdate.get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setUserInfo(user.getUserInfo());
        userRepository.save(updatedUser);
    }

    @Override
    public User delete(String username) throws UserNotFoundException {
        if(!userRepository.existsById(username))
            throw new UserNotFoundException(username);
        User userToDelete = userRepository.findById(username).get();
        for(Group g : userToDelete.getGroups())
            g.getUsers().remove(userToDelete);
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    @Override
    public Set<User> getAllForGroup(long groupId) throws GroupNotFoundException {
        if(!groupRepository.existsById(groupId))
            throw new GroupNotFoundException("Given group does not exist!");
        Set<User> users = groupRepository.findById(groupId).get().getUsers();
        return users.stream().map(u -> {u.setPassword("---"); return u;}).collect(Collectors.toSet());
    }
}
