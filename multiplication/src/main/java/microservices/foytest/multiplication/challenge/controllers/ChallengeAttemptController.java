package microservices.foytest.multiplication.challenge.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.foytest.multiplication.challenge.dto.ChallengeAttemptDTO;
import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.challenge.services.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides a REST API to POST the attempts from users
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {
    private final ChallengeService challengeService;

    @PostMapping
        // using @Valid there is autoconfiguration to handle the errors and build a predefined response which by default is
        // BAD_REQUEST when the object is invalid
    ResponseEntity<ChallengeAttempt> postAttempt(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO) {
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping
        // using RequestParam do the binding with the expected request parameter called alias
    ResponseEntity<List<ChallengeAttempt>> getLastAttempts(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
                challengeService.getAttemptsForUser(alias)
        );
    }
}
