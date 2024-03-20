package microservices.foytest.multiplication.clients;

import lombok.extern.slf4j.Slf4j;
import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.challenge.dto.VerifiedAttemptDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
public class GamificationServiceClient {
    private final RestTemplate restTemplate;
    private final String gamificationServiceUrl;

    public GamificationServiceClient(final RestTemplateBuilder builder,
                                     @Value("${service.gamification.url}") final String gamificationServiceUrl) {

        this.restTemplate = builder.build(); //defaults for restTemplate
        this.gamificationServiceUrl = gamificationServiceUrl;
    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {
        try { //using try catch to avoid the application to stop if the gamification service is not available
            VerifiedAttemptDTO dto = new VerifiedAttemptDTO(attempt.getId(), attempt.isCorrect(),
                    attempt.getFactorA(), attempt.getFactorB(), attempt.getUser().getId(), attempt.getUser().getAlias());
            ResponseEntity<String> response = restTemplate.postForEntity(gamificationServiceUrl + "/attempts", dto, String.class);
            log.info("Gamification service response: {}", response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful(); //returns true if the response code is the range 2xx
        } catch (Exception ex) {
            log.error("Error sending attempt to gamification service", ex);
            return false;
        }
    }
}
