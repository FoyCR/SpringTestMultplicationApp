package microservices.foy.gamification.game.domain;

import lombok.*;

import java.util.List;

/**
 * Representing a row in the leaderboard, it is not saved in the database as it will be
 * created on the fly by aggregating user scores.
 */
@Value //This generates a immutable class. Immutable objects are objects whose state
// cannot be modified after creation
@AllArgsConstructor
public class LeaderBoardRow {
    Long UserId;
    Long totalScore;
    @With // generates a method to clone an object and add a new field value to
    // the copy (in this case, withBadges)
    List<String> badges;

    public LeaderBoardRow(Long userId, Long totalScore) {
        UserId = userId;
        this.totalScore = totalScore;
        this.badges = List.of();
    }
}
