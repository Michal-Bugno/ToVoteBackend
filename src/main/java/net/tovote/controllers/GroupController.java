package net.tovote.controllers;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.exceptions.*;
import net.tovote.security.TokenDecoder;
import net.tovote.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping
    public Group addGroup(@RequestBody Group group, @RequestHeader(name = "Authorization") String token) throws UserNotFoundException {
        String username = TokenDecoder.getUsername(token);
        groupService.add(group, username);
        return group;
    }

    @GetMapping("/owned")
    public Set<Group> getOwnedGroups(@RequestHeader(name = "Authorization") String token) throws UserNotFoundException{
        String username = TokenDecoder.getUsername(token);
        var groups = groupService.getOwnedForUsername(username);
        return groups;
    }

    @GetMapping
    public Set<Group> getAllExceptOwnedGroups(@RequestHeader(name = "Authorization") String token) throws UserNotFoundException{
        String username = TokenDecoder.getUsername(token);
        var groups = groupService.getAllExceptOwnedForUsername(username);
        return groups;
    }

    @PutMapping
    public Group updateGroup(@RequestHeader(name = "Authorization") String token, @RequestBody Group group) throws UserNotFoundException, GroupNotFoundException, BadAuthorizationException {
        String username = TokenDecoder.getUsername(token);
        if(!groupService.checkOwnership(username, group.getGroupId()))
            throw new BadAuthorizationException("You are not authorized to modify this group!");
        groupService.update(group);
        return group;
    }

    @DeleteMapping("/{groupId}")
    public Group deleteGroup(@RequestHeader(name = "Authorization") String token, @PathVariable long groupId)throws UserNotFoundException, GroupNotFoundException, BadAuthorizationException{
        String username = TokenDecoder.getUsername(token);
        if(!groupService.checkOwnership(username, groupId))
            throw new BadAuthorizationException("You are not authorized to delete this group!");
        return groupService.delete(groupId);
    }

    @PostMapping("/{groupId}/{username}")
    public User addUserToGroup(@RequestHeader(name = "Authorization") String token, @PathVariable String groupId, @PathVariable String username)
            throws UserNotFoundException, GroupNotFoundException, BadAuthorizationException, AlreadyAddedException {
        String ownerUsername = TokenDecoder.getUsername(token);
        try {
            long groupIdLong = Long.parseLong(groupId);
            if(!groupService.checkOwnership(ownerUsername, groupIdLong))
                throw new BadAuthorizationException("You are not authorized to add users to this group!");
            if(groupService.checkMembership(username, groupIdLong))
                throw new AlreadyAddedException(username);
            return groupService.addUser(username, groupIdLong);
        }
        catch(NumberFormatException n){
            throw new GroupNotFoundException("No valid group id provided!");
        }

    }

    @DeleteMapping("/{groupId}/{username}")
    public User deleteUserFromGroup(@RequestHeader(name = "Authorization") String token, @PathVariable String groupId, @PathVariable String username) throws UserNotFoundException, GroupNotFoundException, BadAuthorizationException{
        String ownerUsername = TokenDecoder.getUsername(token);
        try {
            long groupIdLong = Long.parseLong(groupId);
            if (!groupService.checkOwnership(ownerUsername, groupIdLong))
                throw new BadAuthorizationException("You are not authorized to delete this group!");
            if(!groupService.checkMembership(username, groupIdLong))
                throw new UserNotFoundException(username);
            return groupService.deleteUser(username, groupIdLong);
        }
        catch(NumberFormatException n){
            throw new GroupNotFoundException("No group with given id!");
        }
    }

}
