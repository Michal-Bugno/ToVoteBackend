package net.tovote.repositories;

import net.tovote.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "SELECT * FROM groups WHERE owner_username = :username", nativeQuery = true)
    Set<Group> findAllOwnedBy(@Param("username") String username);

}
