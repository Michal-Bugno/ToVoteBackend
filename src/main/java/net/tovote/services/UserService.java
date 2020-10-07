package net.tovote.services;

import net.tovote.entities.User;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.UsernameExistsException;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getByUsername(String username) throws UserNotFoundException;
    void add(User user) throws UsernameExistsException;
    void update(User user) throws UserNotFoundException;
    User delete(String username) throws UserNotFoundException;

}
