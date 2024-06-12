package se.lexicon.g49todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.g49todoapi.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //Check if the email exists in the database
    Boolean existsByEmail(String email);

    @Modifying
    @Query("update User u set u.expired = :status where u.email = :email")
    void updateExpiredByEmail(@Param("email") String email, @Param("status") boolean status);


    @Modifying
    @Query("update User u set u.password = :password where u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);


}
