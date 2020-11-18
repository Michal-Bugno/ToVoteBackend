package net.tovote.repositories;


import net.tovote.entities.Group;
import net.tovote.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
