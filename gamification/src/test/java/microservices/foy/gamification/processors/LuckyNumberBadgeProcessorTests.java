package microservices.foy.gamification.processors;

import microservices.foy.gamification.challenge.dto.VerifiedAttemptDTO;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.processors.LuckyNumberBadgeProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LuckyNumberBadgeProcessorTests {
    private LuckyNumberBadgeProcessor processor;

    @BeforeEach
    public void setUp() {
        processor = new LuckyNumberBadgeProcessor();
    }

    @Test
    public void shouldReturnBadgeIfFactorAIsTheNumber() {
        Optional<BadgeType> badgeType = processor.processForOptionalBadge(10, List.of(),
                new VerifiedAttemptDTO(1L, true, 69, 10, 1L, "Foy"));
        assertThat(badgeType).contains(BadgeType.LUCKY_NUMBER);
    }

    @Test
    public void shouldReturnBadgeIfFactorBIsTheNumber() {
        Optional<BadgeType> badgeType = processor.processForOptionalBadge(10, List.of(),
                new VerifiedAttemptDTO(1L, true, 10, 69, 1L, "Foy"));
        assertThat(badgeType).contains(BadgeType.LUCKY_NUMBER);
    }

    @Test
    public void shouldReturnBadgeIfBothFactorsAreTheNumber() {
        Optional<BadgeType> badgeType = processor.processForOptionalBadge(10, List.of(),
                new VerifiedAttemptDTO(1L, true, 69, 69, 1L, "Foy"));
        assertThat(badgeType).contains(BadgeType.LUCKY_NUMBER);
    }

    @Test
    public void shoulNotdReturnBadgeIfNoneFactorsAreTheNumber() {
        Optional<BadgeType> badgeType = processor.processForOptionalBadge(10, List.of(),
                new VerifiedAttemptDTO(1L, true, 10, 10, 1L, "Foy"));
        assertThat(badgeType).isEmpty();
    }
}
