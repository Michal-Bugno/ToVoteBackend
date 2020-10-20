package net.tovote.services;

import net.tovote.entities.User;
import net.tovote.entities.Vote;
import net.tovote.entities.Voting;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;
import net.tovote.repositories.UserRepository;
import net.tovote.repositories.VoteRepository;
import net.tovote.repositories.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotingServiceImplementation implements VotingService{

    private VotingRepository votingRepository;
    private UserRepository userRepository;
    private VoteRepository voteRepository;

    @Autowired
    public VotingServiceImplementation(VotingRepository votingRepository, UserRepository userRepository, VoteRepository voteRepository){
        this.userRepository = userRepository;
        this.votingRepository = votingRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public List<Voting> getAllForUsername(String username) throws UserNotFoundException{
        if(! userRepository.existsById(username))
            throw new UserNotFoundException(username);
        return votingRepository.findAllForUserName(username);
    }

    @Override
    public Voting getById(long id) throws VotingNotFoundException{
        Optional<Voting> voting = votingRepository.findById(id);
        if(voting.isPresent())
            return voting.get();
        throw new VotingNotFoundException("There is no voting with given ID!");
    }

    @Override
    public void add(Voting voting, String username) throws UserNotFoundException{
        Optional<User> user = userRepository.findById(username);
        if(!user.isPresent())
            throw new UserNotFoundException(username);
        voting.setUser(user.get());
        votingRepository.save(voting);
    }

    @Override
    public List<Vote> getAllVotes(long votingId) throws VotingNotFoundException{
        if(!votingRepository.existsById(votingId))
            throw new VotingNotFoundException("Voting with given ID not found!");
        Voting voting = votingRepository.findById(votingId).get();
        Vote exampleVote = new Vote();
        exampleVote.setVoting(voting);
        Example<Vote> example = Example.of(exampleVote);
        List<Vote> votes = voteRepository.findAll(example);
        return votes;
    }

    @Override
    public void submitVote(long votingId, Vote vote) throws VotingNotFoundException{
        Optional<Voting> voting = votingRepository.findById(votingId);
        if(!voting.isPresent())
            throw new VotingNotFoundException("Cannot submit vote to a non-existing voting");
        vote.setVoting(voting.get());
        voteRepository.save(vote);
    }

    @Override
    public void update(Voting voting) throws VotingNotFoundException{
        Optional<Voting> updatedVoting = votingRepository.findById(voting.getVotingId()).map(v -> {
            v.setStartTimeStamp(voting.getStartTimeStamp());
            v.setEndTimeStamp(voting.getEndTimeStamp());
            v.setDescription(voting.getDescription());
            v.setVotingType(voting.getVotingType());
            return votingRepository.save(v);
        });

        if(updatedVoting.isPresent())
            return;

        throw new VotingNotFoundException("There is no voting with given id!");
    }

    @Override
    public Voting deleteById(long id) throws VotingNotFoundException {
        return null;
    }
}
