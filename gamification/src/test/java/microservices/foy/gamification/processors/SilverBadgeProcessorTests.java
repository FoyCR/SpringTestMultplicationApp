package microservices.foy.gamification.processors;

import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.processors.SilverBadgeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SilverBadgeProcessorTests {
    private SilverBadgeProcessor badgeProcessor;

    @BeforeEach
    public void setUp() {
        badgeProcessor = new SilverBadgeProcessor();
    }

    @Test
    public void shouldReturnBadgeIfScoreOverThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(200, List.of(), null);
        assertThat(badgeType).contains(BadgeType.SILVER);
    }

    @Test
    public void shouldNotReturnBadgeIfScoreUnderThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(140, List.of(), null);
        assertThat(badgeType).isEmpty();
    }
}
