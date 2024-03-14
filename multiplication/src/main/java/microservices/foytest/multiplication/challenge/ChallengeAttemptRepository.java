package microservices.foytest.multiplication.challenge;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

//CrudRepository<T,ID> defines a list of basic methods to create, read, update, and delete (CRUD) objects.
// his use the SimpleJpaRepository in Spring Data JPA as implementation.
public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Long> {
    /**
     * @return the las 10 attempts for a given user, identified by their alias
     */
    // method which defines a query by using naming conventions,
    // another way to get the same result is using the @Query annotation
    // @Query("SELECT a FROM ChallengeAttempt a WHERE a.user.alias = :userAlias ORDER BY a.id DESC")
    // List<ChallengeAttempt> lastAttempts(@Param("userAlias") String userAlias);
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);

}
