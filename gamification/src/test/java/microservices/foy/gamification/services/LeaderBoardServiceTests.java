package microservices.foy.gamification.services;

import microservices.foy.gamification.game.data.BadgeRepository;
import microservices.foy.gamification.game.data.ScoreRepository;
import microservices.foy.gamification.game.domain.BadgeCard;
import microservices.foy.gamification.game.domain.BadgeType;
import microservices.foy.gamification.game.domain.LeaderBoardRow;
import microservices.foy.gamification.game.services.LeaderBoardService;
import microservices.foy.gamification.game.services.LeaderBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LeaderBoardServiceTests {
    private LeaderBoardService leaderBoardService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    public void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    public void ShouldReturnLeaderBoard() {
        //Given
        Long userId = 1L;
        BadgeCard badgeCard = new BadgeCard(1L, BadgeType.FIRST_RIGHT);
        LeaderBoardRow scoreRow = new LeaderBoardRow(userId, 300L, List.of());
        List<LeaderBoardRow> expectedLeaderBoard = List.of(
                new LeaderBoardRow(userId, 300L, List.of(BadgeType.FIRST_RIGHT.getDescription())));
        given(scoreRepository.findFirst10Scores()).willReturn(List.of(scoreRow));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(List.of(badgeCard));

        //When
        List<LeaderBoardRow> result = leaderBoardService.getCurrentLeaderBoard();

        //Then
        then(result).isEqualTo(expectedLeaderBoard);

    }
}
