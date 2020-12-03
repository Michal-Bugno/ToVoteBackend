package net.tovote.controllers;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.exceptions.*;
import net.tovote.security.TokenDecoder;
import net.tovote.services.GroupService;
import net.tovote.services.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final VotingService votingService;

    @Autowired
    public GroupController(GroupService groupService, VotingService votingService){
        this.groupService = groupService;
        this.votingService = votingService;
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

    @GetMapping("/voting/{votingId}")
    public Set<Group> getGroupsForVoting(@RequestHeader(name = "Authorization") String token, @PathVariable String votingId) throws VotingNotFoundException, BadAuthorizationException, UserNotFoundException{
        String username = TokenDecoder.getUsername(token);
        long votingIdLong;
        try{
            votingIdLong = Long.parseLong(votingId);
        }
        catch (NumberFormatException n){
            throw new VotingNotFoundException("No valid id provided!");
        }
        return groupService.getAllForVoting(votingIdLong);
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
    public Group deleteGroup(@RequestHeader(name = "Authorization") String token, @PathVariable String groupId) throws UserNotFoundException, GroupNotFoundException, BadAuthorizationException{
        long groupIdLong;
        try{
            groupIdLong = Long.parseLong(groupId);
        }
        catch(NumberFormatException n){
            throw new GroupNotFoundException("No valid ID provided!");
        }
        String username = TokenDecoder.getUsername(token);
        if(!groupService.checkOwnership(username, groupIdLong))
            throw new BadAuthorizationException("You are not authorized to delete this group!");
        return groupService.delete(groupIdLong);
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
