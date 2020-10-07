package net.tovote.services;

import net.tovote.entities.User;
import net.tovote.entities.Voting;
import net.tovote.exceptions.UserNotFoundException;
import net.tovote.exceptions.VotingNotFoundException;
import net.tovote.repositories.UserRepository;
import net.tovote.repositories.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingServiceImplementation implements VotingService{

    private VotingRepository votingRepository;
    private UserRepository userRepository;

    @Autowired
    public VotingServiceImplementation(VotingRepository votingRepository, UserRepository userRepository){
        this.userRepository = userRepository;
        this.votingRepository = votingRepository;
    }

    @Override
    public List<Voting> getAllForUsername(String username) throws UserNotFoundException{
        if(! userRepository.existsById(username))
            throw new UserNotFoundException(username);
        Voting exampleVoting = new Voting();
        User exampleUser = new User();
        exampleUser.setUsername(username);
        exampleVoting.setUser(exampleUser);
        Example<Voting> example = Example.of(exampleVoting);
        return votingRepository.findAll(example);
    }

    @Override
    public Voting getById(long id) throws VotingNotFoundException{
        return null;
    }

    @Override
    public void add(Voting voting) {
        votingRepository.save(voting);
    }

    @Override
    public Voting deleteById(long id) throws VotingNotFoundException {
        return null;
    }
}
