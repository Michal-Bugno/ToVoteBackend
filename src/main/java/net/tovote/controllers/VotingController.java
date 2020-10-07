package net.tovote.controllers;

import net.tovote.entities.Voting;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.services.UserService;
import net.tovote.services.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votings")
public class VotingController {

    private VotingService votingService;
    //private UserService userService

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @GetMapping("/{username}")
    public List<Voting> getVotingsByUsername(@PathVariable String username) throws UserNotFoundException {
        return votingService.getAllForUsername(username);
    }

    @PostMapping
    public Voting addVoting(@RequestBody Voting voting){
        votingService.add(voting);
        return voting;
    }
}
