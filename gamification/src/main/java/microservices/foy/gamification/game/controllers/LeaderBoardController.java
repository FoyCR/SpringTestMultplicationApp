package microservices.foy.gamification.game.controllers;

import lombok.RequiredArgsConstructor;
import microservices.foy.gamification.game.domain.LeaderBoardRow;
import microservices.foy.gamification.game.services.LeaderBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaders")
@RequiredArgsConstructor
public class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;

    @GetMapping
    private List<LeaderBoardRow> getLeaderBoard() {
        return leaderBoardService.getCurrentLeaderBoard();
    }
}
