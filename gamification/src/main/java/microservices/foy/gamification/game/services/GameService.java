package microservices.foy.gamification.game.services;

import lombok.Value;
import microservices.foy.gamification.challenge.dto.AttemptVerifiedEvent;
import microservices.foy.gamification.game.domain.BadgeType;

import java.util.List;

public interface GameService {
    /**
     * Process a new attempt already verified from a given user
     *
     * @param attempt the attempt already verified whether is correct not
     * @return a {@link GameResult} object containing the new score and badge cards obtained if any
     */
    GameResult newAttemptForUser(AttemptVerifiedEvent attempt);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }

}
