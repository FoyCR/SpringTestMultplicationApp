package microservices.foy.gamification.game.data;

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

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);


}
