package microservices.foy.gamification.processors;

import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.processors.BronzeBadgeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BronzeBadgeProcessorTest {
    private BronzeBadgeProcessor badgeProcessor;

    @BeforeEach
    public void setUp() {
        badgeProcessor = new BronzeBadgeProcessor();
    }

    @Test
    public void shouldReturnBadgeIfScoreOverThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(50, List.of(), null);
        assertThat(badgeType).contains(BadgeType.BRONZE);
    }

    @Test
    public void shouldNotReturnBadgeIfScoreUnderThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(40, List.of(), null);
        assertThat(badgeType).isEmpty();
    }
}
