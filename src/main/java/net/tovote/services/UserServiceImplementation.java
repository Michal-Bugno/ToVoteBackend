package net.tovote.services;

import net.tovote.entities.User;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.UsernameExistsException;
import net.tovote.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{


    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if(!user.isPresent())
            throw new UserNotFoundException(username);
        return user.get();
    }

    @Override
    public void add(User user) throws UsernameExistsException {
        if(userRepository.existsById(user.getUsername()))
            throw new UsernameExistsException(user.getUsername());
        userRepository.save(user);
    }

    @Override
    public void update(User user) throws UserNotFoundException {
        Optional<User> updatedUser = userRepository.findById(user.getUsername()).map(u -> {
            u.setEmail(user.getEmail());
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setPassword(user.getPassword());
            return userRepository.save(u);
        });

        if(updatedUser.isPresent())
            return;

        throw new UserNotFoundException(user.getUsername());

    }

    @Override
    public User delete(String username) throws UserNotFoundException {
        if(!userRepository.existsById(username))
            throw new UserNotFoundException(username);
        User userToDelete = userRepository.findById(username).get();
        userRepository.delete(userToDelete);
        return userToDelete;
    }
}
