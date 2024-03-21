package microservices.foy.gamification.game.processors;

import lombok.extern.slf4j.Slf4j;
import microservices.foy.gamification.challenge.dto.VerifiedAttemptDTO;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component //Required to be injected by Spring
public class FirstRightBadgeProcessor implements BadgeProcessor {

    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, VerifiedAttemptDTO attempt) {
        log.info("First Right Badge Processor, number of current score cards: {}", scoreCards.size());
        return scoreCards.size() == 1 ? Optional.of(BadgeType.FIRST_RIGHT) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_RIGHT;
    }
}
