package net.tovote.controllers;

import net.tovote.entities.User;
import net.tovote.exceptions.UserException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.UsernameExistsException;
import net.tovote.requests.LoginData;
import net.tovote.responses.ErrorResponse;
import net.tovote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAll();
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) throws UserNotFoundException {
        return userService.getByUsername(username);
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws UsernameExistsException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.add(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws UserNotFoundException{
        userService.update(user);
        return user;
    }

    @DeleteMapping("/{username}")
    public User deleteUser(@PathVariable String username) throws UserNotFoundException {
        return userService.delete(username);
    }
}
