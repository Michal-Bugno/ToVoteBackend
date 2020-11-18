package net.tovote.services;

import net.tovote.entities.Group;
import net.tovote.entities.User;
import net.tovote.entities.Vote;
import net.tovote.entities.Voting;
import net.tovote.exceptions.AlreadyVotedException;
import net.tovote.exceptions.GroupNotFoundException;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;
import net.tovote.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VotingServiceImplementation implements VotingService{

    private VotingRepository votingRepository;
    private UserRepository userRepository;
    private VoteRepository voteRepository;
    private GroupRepository groupRepository;
    private VotingOptionRepository votingOptionRepository;

    @Autowired
    public VotingServiceImplementation(VotingRepository votingRepository, UserRepository userRepository, VoteRepository voteRepository, GroupRepository groupRepository, VotingOptionRepository votingOptionRepository){
        this.userRepository = userRepository;
        this.votingRepository = votingRepository;
        this.voteRepository = voteRepository;
        this.groupRepository = groupRepository;
        this.votingOptionRepository = votingOptionRepository;
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
    public Group addGroup(long votingId, long groupId) throws VotingNotFoundException, GroupNotFoundException {
        Optional<Voting> voting = votingRepository.findById(votingId);
        if(voting.isEmpty())
            throw new VotingNotFoundException("No voting with given ID!");
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty())
            throw new GroupNotFoundException("No group with given ID!");
        voting.get().addGroup(group.get());
        votingRepository.save(voting.get());
        return group.get();
    }

    @Override
    public Group deleteGroup(long votingId, long groupId) throws VotingNotFoundException, GroupNotFoundException {
        Optional<Voting> voting = votingRepository.findById(votingId);
        if(voting.isEmpty())
            throw new VotingNotFoundException("No voting with given ID!");
        Optional<Group> group = groupRepository.findById(groupId);
        if(group.isEmpty())
            throw new GroupNotFoundException("No group with given ID!");
        voting.get().removeGroup(group.get());
        votingRepository.deleteById(votingId);
        return group.get();
    }

    @Override
    public boolean checkOwnership(String username, long votingId) throws UserNotFoundException, VotingNotFoundException {
        Optional<Voting> voting = votingRepository.findById(votingId);
        if(voting.isEmpty())
            throw new VotingNotFoundException("No voting with given ID!");
        if(voting.get().getUser().getUsername().equals(username))
            return true;
        return false;
    }

    @Override
    public Set<Vote> getAllVotes(long votingId) throws VotingNotFoundException{
        var voting = votingRepository.findById(votingId);
        if(voting.isEmpty())
            throw new VotingNotFoundException("Voting with given ID not found!");
        Set<Vote> votes = voting.get().getVotes();
        if(!voting.get().isExplicit())
            return votes.stream().map(v -> {v.setUser(null); return v;}).collect(Collectors.toSet());
        return votes;
    }

    @Override
    public void submitVote(long votingId, Vote vote) throws VotingNotFoundException, AlreadyVotedException {
        Optional<Voting> voting = votingRepository.findById(votingId);
        if(!voting.isPresent())
            throw new VotingNotFoundException("Cannot submit vote to a non-existing voting!");
        for(Vote v : voting.get().getVotes())
            if(v.getUser().getUsername().equals(vote.getUser().getUsername()))
                throw new AlreadyVotedException(v.getUser().getUsername());
        voting.get().getVotes().add(vote);
        votingRepository.save(voting.get());
    }

    @Override
    public void update(Voting voting) throws VotingNotFoundException{
        Optional<Voting> updatedVoting = votingRepository.findById(voting.getVotingId()).map(v -> {
            v.setStartTimeStamp(voting.getStartTimeStamp());
            v.setEndTimeStamp(voting.getEndTimeStamp());
            v.setDescription(voting.getDescription());
            v.setVotingType(voting.getVotingType());
            v.setName(voting.getName());
            return votingRepository.save(v);
        });

        if(updatedVoting.isPresent())
            return;

        throw new VotingNotFoundException("There is no voting with given id!");
    }

    @Override
    public Voting deleteById(long id) throws VotingNotFoundException{
        var voting = votingRepository.findById(id);
        if(voting.isEmpty())
            throw new VotingNotFoundException("No voting with given ID!");
        votingRepository.deleteById(id);
        return voting.get();
    }
}
