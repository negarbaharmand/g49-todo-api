package se.lexicon.g49todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.lexicon.g49todoapi.domain.entity.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    //find all persons with no task

    @Query("select p from Person p where size(p.tasks) = 0")
    List<Person> findIdlePeople();

}
