package net.tovote.services;

import net.tovote.entities.User;
import net.tovote.exceptions.EmailInUseException;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.UsernameExistsException;

import java.util.List;
import java.util.Set;

public interface UserService {

    User getByUsername(String username) throws UserNotFoundException;
    void add(User user) throws UsernameExistsException, EmailInUseException;
    void update(User user) throws UserNotFoundException;
    User delete(String username) throws UserNotFoundException;
    Set<User> getAllForGroup(long groupId) throws GroupNotFoundException;
}
