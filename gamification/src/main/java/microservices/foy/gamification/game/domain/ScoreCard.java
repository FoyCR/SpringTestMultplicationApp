package microservices.foy.gamification.game.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents the score (points) linked to an attempt in the game,
 * with an associated user and the timestamp in which score is registered
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreCard {
    // the default score
    public static final int DEFAULT_SCORE = 10;
    @Id
    @GeneratedValue
    private Long cardId;
    private Long userId;
    private Long attemptId;
    @EqualsAndHashCode.Exclude //exclude the property in the Equals and HashCode Methods
    private long scoreTimestamp;
    private int score;

    public ScoreCard(final Long userId, final Long attemptId) {
        this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
    }
}
