package net.tovote.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.tovote.entities.Vote;
import net.tovote.entities.Voting;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;
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

    private VotingService votingService;
    private GroupService groupService;

    @Autowired
    public VotingController(VotingService votingService, GroupService groupService) {
        this.votingService = votingService;
        this.groupService = groupService;
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

    @PostMapping("/{votingId}/votes")
    public Vote submitVote(@RequestBody Vote vote, @PathVariable long votingId) throws VotingNotFoundException {
        votingService.submitVote(votingId, vote);
        return vote;
    }

    @GetMapping("/{votingId}/votes")
    public Set<Vote> getVotes(@PathVariable long votingId) throws VotingNotFoundException{
        Set<Vote> votes = votingService.getAllVotes(votingId);
        return votes;
    }

    //private boolean authorizeVote(long votingId, )
}
