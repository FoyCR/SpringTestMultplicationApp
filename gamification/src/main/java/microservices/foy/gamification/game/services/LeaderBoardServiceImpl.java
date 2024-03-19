package microservices.foy.gamification.game.services;

import lombok.RequiredArgsConstructor;
import microservices.foy.gamification.game.data.BadgeRepository;
import microservices.foy.gamification.game.data.ScoreRepository;
import microservices.foy.gamification.game.domain.LeaderBoardRow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        List<LeaderBoardRow> scoreOnly = scoreRepository.findFirst10Scores();
        return scoreOnly
                .stream()
                .map(this::getLeaderRowWithBadges)
                .toList();
    }

    private LeaderBoardRow getLeaderRowWithBadges(LeaderBoardRow row) {
        List<String> badges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(row.getUserId())
                .stream()
                .map(b -> b.getBadgeType().getDescription())
                .toList();
        return row.withBadges(badges);
    }

}
