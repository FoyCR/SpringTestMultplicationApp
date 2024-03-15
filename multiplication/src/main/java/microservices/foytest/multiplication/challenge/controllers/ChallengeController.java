package microservices.foytest.multiplication.challenge.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.foytest.multiplication.challenge.domain.Challenge;
import microservices.foytest.multiplication.challenge.services.ChallengeGeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class implements a REST API to get random challenges
 */
@Slf4j  //logging, enables the log object
@RequiredArgsConstructor //create a constructor with the variables private and final (ChallengeGeneratorService)
//Spring uses dependency injection so the only candidate for ChallengeGeneratorService is ChallengeGeneratorServiceImpl
@RestController  //rest controller annotation, the response is JSON by default
@RequestMapping("/challenges") //map API context
public class ChallengeController {
    private final ChallengeGeneratorService challengeGeneratorService;

    @GetMapping("/random")
    Challenge getRandomChallenge() {
        Challenge challenge = challengeGeneratorService.randomChallenge();
        log.info("Random challenge generated: {}", challenge);
        return challenge;
    }
}