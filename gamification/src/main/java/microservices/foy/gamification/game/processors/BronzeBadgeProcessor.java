package microservices.foy.gamification.game.processors;

import lombok.extern.slf4j.Slf4j;
import microservices.foy.gamification.challenge.dto.AttemptVerifiedEvent;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class BronzeBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, AttemptVerifiedEvent attempt) {
        log.info("Bronze Badge Processor, current score: {}", currentScore);
        return currentScore >= 50 ? Optional.of(BadgeType.BRONZE) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
