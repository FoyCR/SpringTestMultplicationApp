package microservices.foy.gamification.processors;

import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.domain.ScoreCard;
import microservices.foy.gamification.game.processors.FirstRightBadgeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

public class FirstRightBadgeProcessorTests {
    private FirstRightBadgeProcessor processor;

    @BeforeEach
    public void setUp() {
        processor = new FirstRightBadgeProcessor();
    }

    @Test
    public void shouldReturnBadgeIfThereIsOneScoreCard() {
        Optional<BadgeType> badgeType = processor.processForOptionalBadge(10, List.of(new ScoreCard(1L, 1L)), null);
        assertThat(badgeType).contains(BadgeType.FIRST_RIGHT);
    }

    @Test
    public void shouldNotReturnBadgeIfThereMoreScorecards() {
        Optional<BadgeType> badgeType = processor.processForOptionalBadge(10,
                List.of(new ScoreCard(1L, 1L), new ScoreCard(1L, 1L)), null);
        assertThat(badgeType).isEmpty();
    }

    @Test
    public void shouldNotReturnBadgeIfThereIsNoScoreCards() {
        Optional<BadgeType> badgeType = processor.processForOptionalBadge(10, List.of(), null);
        assertThat(badgeType).isEmpty();
    }

}
