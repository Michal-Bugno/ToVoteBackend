package net.tovote.repositories;


import net.tovote.entities.User;
import net.tovote.entities.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {

    @Query(value = "SELECT * FROM votings WHERE user_username = :username", nativeQuery = true)
    List<Voting> findAllForUserName(@Param("username") String username);

}
