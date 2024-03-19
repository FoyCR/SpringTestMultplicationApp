package microservices.foy.gamification.processors;

import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.processors.GoldBadgeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldBadgeProcessorTests {
    private GoldBadgeProcessor badgeProcessor;

    @BeforeEach
    public void setUp() {
        badgeProcessor = new GoldBadgeProcessor();
    }

    @Test
    public void shouldReturnBadgeIfScoreOverThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(450, List.of(), null);
        assertThat(badgeType).contains(BadgeType.GOLD);
    }

    @Test
    public void shouldNotReturnBadgeIfScoreUnderThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(390, List.of(), null);
        assertThat(badgeType).isEmpty();
    }
}
