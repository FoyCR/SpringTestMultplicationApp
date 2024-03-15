package microservices.foy.gamification.game.domain;

import lombok.*;
import jakarta.persistence.*;

/**
 * Represents a Badge earned by a user.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeCard {
    @Id
    @GeneratedValue
    private Long badgeId;
    private Long userId;
    @EqualsAndHashCode.Exclude
    private long badgeTimestamp;
    private BadgeType badgeType;

    public BadgeCard(final Long userId, final BadgeType badgeType) {
        this(null, userId, System.currentTimeMillis(), badgeType);
    }
}
