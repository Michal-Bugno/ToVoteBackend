package net.tovote.controllers;

import net.tovote.entities.User;
import net.tovote.exceptions.*;
import net.tovote.requests.LoginData;
import net.tovote.responses.ErrorResponse;
import net.tovote.security.TokenDecoder;
import net.tovote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) throws UserNotFoundException {
        return userService.getByUsername(username);
    }

    @GetMapping("/group//{groupId}")
    public Set<User> getUsersForGroup(@PathVariable String groupId) throws GroupNotFoundException {
        try {
            long groupIdLong = Long.parseLong(groupId);
            return userService.getAllForGroup(groupIdLong);
        }
        catch(NumberFormatException n){
            throw new GroupNotFoundException("No valid id provided!");
        }
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws UsernameExistsException, EmailInUseException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.add(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user, @RequestHeader(name = "Authorization") String token) throws UserNotFoundException, BadAuthorizationException{
        String subject = TokenDecoder.getUsername(token);
        if(!subject.equals(user.getUsername()))
            throw new BadAuthorizationException("Your identity and provided username do not match!");
        userService.update(user);
        return user;
    }

    @DeleteMapping
    public User deleteUser(@RequestHeader String token) throws UserNotFoundException {
        String username = TokenDecoder.getUsername(token);
        return userService.delete(username);
    }
}
