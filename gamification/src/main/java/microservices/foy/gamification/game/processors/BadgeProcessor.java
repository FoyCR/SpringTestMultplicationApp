package microservices.foy.gamification.game.processors;

import microservices.foy.gamification.challenge.dto.VerifiedAttemptDTO;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    /**
     * Processes some of all the passed parameters to determine if the user os entitled to a badge
     *
     * @param currentScore current score for a user
     * @param scoreCards   list of scorecards of a user
     * @param attempt      current verified attempt
     * @return a BadgeType if the user is entitled to this badge, otherwise empty
     */
    Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, VerifiedAttemptDTO attempt);

    /**
     * @return the BadgeType object that this processor is handling. Can be used to filter processor according the needs
     */
    BadgeType badgeType();
}
