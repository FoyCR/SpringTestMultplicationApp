package microservices.foy.gamification.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import microservices.foy.gamification.challenge.dto.VerifiedAttemptDTO;
import microservices.foy.gamification.game.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postAttempt(@RequestBody @Valid VerifiedAttemptDTO dto) {
        gameService.newAttemptForUser(dto);
    }
}
