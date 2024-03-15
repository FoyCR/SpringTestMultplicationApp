package microservices.foy.gamification.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Different types of Badges that a user can win
 */
@RequiredArgsConstructor
@Getter
public enum BadgeType {
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    FIRST_RIGHT("First time"),
    LUCKY_NUMBER("Lucky number");
    private final String description;
}
