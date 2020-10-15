package net.tovote.repositories;

import net.tovote.entities.VotingOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingOptionRepository extends JpaRepository<VotingOption, Long> {
}
