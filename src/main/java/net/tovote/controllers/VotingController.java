package net.tovote.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.tovote.entities.Group;
import net.tovote.entities.Vote;
import net.tovote.entities.Voting;
import net.tovote.exceptions.*;
import net.tovote.security.SecurityConstants;
import net.tovote.security.TokenDecoder;
import net.tovote.services.GroupService;
import net.tovote.services.UserService;
import net.tovote.services.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/votings")
public class VotingController {

    private final VotingService votingService;
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public VotingController(VotingService votingService, GroupService groupService, UserService userService) {
        this.votingService = votingService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping
    public List<Voting> getVotingsByUsername(@RequestHeader(name = "Authorization") String token) throws UserNotFoundException {
        return votingService.getAllForUsername(TokenDecoder.getUsername(token));
    }

    @PostMapping
    public Voting addVoting(@RequestBody Voting voting, @RequestHeader(name = "Authorization") String token) throws UserNotFoundException{
        String username = TokenDecoder.getUsername(token);
        votingService.add(voting, username);
        return voting;
    }

    @PutMapping
    public Voting updateVoting(@RequestBody Voting voting, @RequestHeader(name = "Authorization") String token)
            throws VotingNotFoundException, BadAuthorizationException, UserNotFoundException {
        String username = TokenDecoder.getUsername(token);
        if(!votingService.checkOwnership(username, voting.getVotingId()))
            throw new BadAuthorizationException("You are not authorized to edit this voting!");
        votingService.update(voting);
        return voting;
    }

    @DeleteMapping("/{votingId}")
    public Voting deleteVoting(@RequestHeader(name = "Authorization") String token, @PathVariable String votingId)
            throws VotingNotFoundException, BadAuthorizationException, UserNotFoundException {
        String username = TokenDecoder.getUsername(token);
        long votingIdLong;
        try{
            votingIdLong = Long.parseLong(votingId);
        }
        catch(NumberFormatException n){
            throw new VotingNotFoundException("No valid id provided!");
        }
        if(!votingService.checkOwnership(username, votingIdLong))
            throw new BadAuthorizationException("You are not authorized to delete this voting!");
        return votingService.deleteById(votingIdLong);
    }

    @PostMapping("/{votingId}/{groupId}")
    public Group addGroupToVoting(@RequestHeader(name = "Authorization") String token, @PathVariable String votingId, @PathVariable String groupId)
            throws VotingNotFoundException, GroupNotFoundException, AlreadyAddedException, BadAuthorizationException, UserNotFoundException {
        String username = TokenDecoder.getUsername(token);
        long votingIdLong;
        long groupIdLong;
        try{
            votingIdLong = Long.parseLong(votingId);
        }
        catch(NumberFormatException n){
            throw new VotingNotFoundException("No valid id provided!");
        }
        try{
            groupIdLong = Long.parseLong(groupId);
        }
        catch(NumberFormatException n){
            throw new GroupNotFoundException("No valid id provided!");
        }
        if(!votingService.checkOwnership(username, votingIdLong))
            throw new BadAuthorizationException("You are not authorized to add groups to this voting!");
        Group group = votingService.addGroup(votingIdLong, groupIdLong);
        return group;
    }

    @DeleteMapping("/{votingId}/{groupId}")
    public Group deleteGroupFromVoting(@RequestHeader(name = "Authorization") String token, @PathVariable String votingId, @PathVariable String groupId)
            throws VotingNotFoundException, GroupNotFoundException, UserNotFoundException, BadAuthorizationException{
        String username = TokenDecoder.getUsername(token);
        long votingIdLong;
        long groupIdLong;
        try{
            votingIdLong = Long.parseLong(votingId);
        }
        catch(NumberFormatException n){
            throw new VotingNotFoundException("No valid id provided!");
        }
        try{
            groupIdLong = Long.parseLong(groupId);
        }
        catch(NumberFormatException n){
            throw new GroupNotFoundException("No valid id provided!");
        }
        if(!votingService.checkOwnership(username, votingIdLong))
            throw new BadAuthorizationException("You are not authorized to add groups to this voting!");
        return votingService.deleteGroup(votingIdLong, groupIdLong);
    }

    @PostMapping("/{votingId}/votes")
    public Vote submitVote(@RequestHeader(name = "Authorization") String token, @RequestBody Vote vote, @PathVariable String votingId)
            throws VotingNotFoundException, AlreadyVotedException, UserNotFoundException, GroupNotFoundException, BadAuthorizationException {
        String username = TokenDecoder.getUsername(token);
        long votingIdLong;
        try{
            votingIdLong = Long.parseLong(votingId);
        }
        catch(NumberFormatException n){
            throw new VotingNotFoundException("No valid id provided!");
        }
        if(!authorizeVote(username, votingIdLong))
            throw new BadAuthorizationException("You are not authorized to vote in this voting!");
        vote.setUser(userService.getByUsername(username));
        votingService.submitVote(votingIdLong, vote);
        return vote;
    }

    @GetMapping("/{votingId}/votes")
    public Set<Vote> getVotes(@RequestHeader(name = "Authorization") String token, @PathVariable String votingId)
            throws VotingNotFoundException, GroupNotFoundException, UserNotFoundException, BadAuthorizationException{
        String username = TokenDecoder.getUsername(token);
        long votingIdLong;
        try{
            votingIdLong = Long.parseLong(votingId);
        }
        catch(NumberFormatException n){
            throw new VotingNotFoundException("No valid id provided!");
        }
        if(!authorizeVote(username, votingIdLong))
            throw new BadAuthorizationException("Not authorize to view these votes!");
        Set<Vote> votes = votingService.getAllVotes(votingIdLong);
        return votes;
    }

    private boolean authorizeVote(String username, long votingId) throws VotingNotFoundException, GroupNotFoundException, UserNotFoundException{
        Voting voting = votingService.getById(votingId);
        var groups = voting.getGroups();
        for(var g : groups){
            if(groupService.checkMembership(username, g.getGroupId()) || groupService.checkOwnership(username, g.getGroupId()))
                return true;
        }
        return false;
    }
}
