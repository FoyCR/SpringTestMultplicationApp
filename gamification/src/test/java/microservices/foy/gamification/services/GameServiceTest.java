package microservices.foy.gamification.services;

import microservices.foy.gamification.challenge.dto.VerifiedAttemptDTO;
import microservices.foy.gamification.game.data.ScoreRepository;
import microservices.foy.gamification.game.data.BadgeRepository;
import microservices.foy.gamification.game.domain.BadgeCard;
import microservices.foy.gamification.game.domain.ScoreCard;
import microservices.foy.gamification.game.processors.BadgeProcessor;
import microservices.foy.gamification.game.services.GameService;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.services.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    private GameService gameService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    BadgeProcessor badgeProcessor;

    @BeforeEach
    public void SetUp() {
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, List.of(badgeProcessor));
    }

    @Test
    public void HandleSuccessfulAttempt() {
        //given
        long userId = 1L, attemptId = 1L;
        VerifiedAttemptDTO attemptDTO = new VerifiedAttemptDTO(attemptId, true, 50, 30, userId, "Foy");
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);

        GameService.GameResult expectedResult = new GameService.GameResult(10, List.of((BadgeType.LUCKY_NUMBER)));
        given(scoreRepository.getTotalScoreForUser(userId)).willReturn(Optional.of(10));
        given(scoreRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(List.of(scoreCard));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(List.of(new BadgeCard(userId, BadgeType.FIRST_RIGHT)));
        given(badgeProcessor.badgeType()).willReturn(BadgeType.LUCKY_NUMBER);
        given(badgeProcessor.processForOptionalBadge(10, List.of(scoreCard), attemptDTO)).willReturn(Optional.of(BadgeType.LUCKY_NUMBER));


        // when
        GameService.GameResult result = gameService.newAttemptForUser(attemptDTO);

        //Then
        then(result).isEqualTo(expectedResult);
        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER)));
    }

    @Test
    public void HandleFailedAttempt() {
        //given
        VerifiedAttemptDTO attemptDTO = new VerifiedAttemptDTO(1L, false, 50, 30, 1L, "Foy");
        GameService.GameResult expectedResult = new GameService.GameResult(0, List.of());

        // when
        GameService.GameResult result = gameService.newAttemptForUser(attemptDTO);

        //Then
        then(result).isEqualTo(expectedResult);
    }
}
