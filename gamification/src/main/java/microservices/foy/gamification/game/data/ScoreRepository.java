package microservices.foy.gamification.game.data;

import microservices.foy.gamification.game.domain.LeaderBoardRow;
import microservices.foy.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends CrudRepository<ScoreCard, Long> {
    /**
     * Gets the total score for a give user id
     *
     * @param userId id of the user
     * @return current total score for the given user
     */
    @Query("SELECT SUM(s.score) FROM ScoreCard s WHERE s.userId= :userId GROUP BY s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") Long userId);

    /**
     * Find all the scores for a given user id order by timestamp
     *
     * @param userId the id of the user
     * @return all the scorecard for a given user
     */
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);

    /**
     * Retrieve a list of {@link LeaderBoardRow} representing the leader board
     *
     * @return the leader board sorted by highest score first
     */
    // This uses JPQL JPA Query Language to uses aggregates and to use NEW and java classes in the query
    @Query("SELECT NEW microservices.foy.gamification.game.domain.LeaderBoardRow(s.userId, SUM(s.score)) " +
            "FROM ScoreCard s GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10Scores();

}
