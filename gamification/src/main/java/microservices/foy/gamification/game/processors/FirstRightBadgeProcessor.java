package microservices.foy.gamification.game.processors;

import microservices.foy.gamification.challenge.dto.VerifiedAttemptDTO;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public class FirstRightBadgeProcessor implements BadgeProcessor {

    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, VerifiedAttemptDTO attempt) {
        return scoreCards.size() == 1 ? Optional.of(BadgeType.FIRST_RIGHT) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_RIGHT;
    }
}
