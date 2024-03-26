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
public class LuckyNumberBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, AttemptVerifiedEvent attempt) {
        log.info("Lucky Number Badge Processor, current attempt FactorA {} factorB {}", attempt.getFactorA(), attempt.getFactorB());
        return (attempt.getFactorA() == 69 || attempt.getFactorB() == 69) ? Optional.of(BadgeType.LUCKY_NUMBER) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.LUCKY_NUMBER;
    }
}
