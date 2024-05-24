package jsl.security.usermanagment.repository;

import jsl.security.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, String> {
    @Query("""
            select p from Person p where p.username = :username
            """)
    Optional<Person> findByUsername(@Param("username") String username);
    void deleteByUsername(String username);
}
