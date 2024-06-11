package se.lexicon.g49todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.g49todoapi.entity.Role;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //select * from role where name = ?
    Optional<Role> findByName(String name);

}
