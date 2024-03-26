package microservices.foytest.multiplication.clients;

import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;

import microservices.foytest.multiplication.challenge.dto.AttemptVerifiedEvent;
//import microservices.foytest.multiplication.challenge.dto.VerifiedAttemptDTO;
import microservices.foytest.multiplication.user.domain.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class) //init mocks
public class GamificationServiceClientTest {
    private static final String GAMIFICATION_URL = "http://localhost:8081";

    @Mock
    private RestTemplateBuilder restTemplateBuilder;
    @Mock
    private RestTemplate restTemplate;

    private GamificationServiceClient gamificationServiceClient;

    private ChallengeAttempt attempt;

    @BeforeEach
    public void setUp() {
        attempt = new ChallengeAttempt(1L, new User(1L, "Foy"), 20, 30, 600, true);
        given(restTemplateBuilder.build()).willReturn(restTemplate);
        gamificationServiceClient = new GamificationServiceClient(restTemplateBuilder, GAMIFICATION_URL);
    }

    @Test
    void testSendAttemptSuccessful() {
        // given
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Success", HttpStatus.OK);
        given(restTemplate.postForEntity(any(String.class), any(AttemptVerifiedEvent.class), eq(String.class)))
                .willReturn(mockResponse);

        // when
        boolean result = gamificationServiceClient.sendAttempt(attempt);

        // then
        then(result).isTrue();
    }

    @Test
    void testSendAttemptFailure() {

        // given
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        given(restTemplate.postForEntity(any(String.class), any(AttemptVerifiedEvent.class), eq(String.class)))
                .willReturn(mockResponse);

        // when
        boolean result = gamificationServiceClient.sendAttempt(attempt);

        // then
        then(result).isFalse();
    }

    @Test
    void testSendAttemptException() {
        //given
        // this simulates an error in the RestTemplate.postForEntity method
        given(restTemplate.postForEntity(any(String.class), any(AttemptVerifiedEvent.class), eq(String.class)))
                .willThrow(new RuntimeException("Test Error occurred"));

        // When
        boolean result = gamificationServiceClient.sendAttempt(attempt);

        // Then
        then(result).isFalse();
    }
}
