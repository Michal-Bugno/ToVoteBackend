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
        if(user.isEmpty())
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

    }

    @Override
    public User delete(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if(user.isEmpty())
            throw new UserNotFoundException(username);
        userRepository.deleteById(username);
        return user.get();
    }
}
