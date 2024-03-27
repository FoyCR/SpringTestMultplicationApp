package microservices.foy.gamification.game.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.foy.gamification.challenge.dto.AttemptVerifiedEvent;
import microservices.foy.gamification.game.data.BadgeRepository;
import microservices.foy.gamification.game.data.ScoreRepository;
import microservices.foy.gamification.game.domain.BadgeCard;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.domain.ScoreCard;
import microservices.foy.gamification.game.processors.BadgeProcessor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    //Spring will inject all the @Component beans in this list
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(AttemptVerifiedEvent attempt) {
        if (attempt.isCorrect()) {
            ScoreCard scoreCard = saveScoreCard(attempt);

            List<BadgeCard> badgeCards = processForBadges(attempt);

            return new GameResult(scoreCard.getScore(), badgeCards.stream()
                    .map(BadgeCard::getBadgeType).collect(Collectors.toList()));
        } else {
            log.info("Attempt id {} is not correct No points this time for user {}.", attempt.getAttemptId(), attempt.getUserAlias());
            return new GameResult(0, List.of());
        }
    }

    /**
     * Checks the total score and the different scorecards obtained to give a new badges in case their conditions are met
     *
     * @param successfulAttempt successful attempt to check
     * @return List of Badges obtained
     */
    private List<BadgeCard> processForBadges(final AttemptVerifiedEvent successfulAttempt) {
        Optional<Integer> optTotalScore = scoreRepository.getTotalScoreForUser(successfulAttempt.getUserId());
        if (optTotalScore.isEmpty()) return Collections.emptyList();
        int totalScore = optTotalScore.get();

        // Gets the total score and existing badges for that user
        List<ScoreCard> scoreCardList = scoreRepository.findByUserIdOrderByScoreTimestampDesc(successfulAttempt.getUserId());
        log.info("Score cards for user {}: {}", successfulAttempt.getUserId(), scoreCardList.size());

        Set<BadgeType> alreadyGotBadges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(successfulAttempt.getUserId())
                .stream().map(BadgeCard::getBadgeType).collect(Collectors.toSet());
        log.info("User {} already got {} badges", successfulAttempt.getUserId(), alreadyGotBadges.size());

        //call the badges processors for badges that the user does not have yet
        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, successfulAttempt))
                .flatMap(Optional::stream)
                .map(badgeType -> new BadgeCard(successfulAttempt.getUserId(), badgeType))
                .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCards); //save new badgeCards in the database
        return newBadgeCards;
    }

    private ScoreCard saveScoreCard(AttemptVerifiedEvent attempt) {
        ScoreCard scoreCard = new ScoreCard(attempt.getUserId(), attempt.getAttemptId()); //score default 10 pints
        scoreRepository.save(scoreCard); // save to database only ids and score
        log.info("User {} score {} points for attempt id {}", attempt.getUserId(), scoreCard.getScore(), attempt.getAttemptId());
        return scoreCard;
    }

}
