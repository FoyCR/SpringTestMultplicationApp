package microservices.foy.gamification.controllers;

import microservices.foy.gamification.challenge.dto.AttemptVerifiedEvent;
import microservices.foy.gamification.game.controllers.GameController;
import microservices.foy.gamification.game.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(GameController.class)
public class GameControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    @Autowired
    private JacksonTester<AttemptVerifiedEvent> jsonAttemptRequest;

    @Test
    public void PostValidAttempt() throws Exception {
        //Given
        AttemptVerifiedEvent attempt = new AttemptVerifiedEvent(1L, true, 60, 40, 1L, "Foy");
        given(gameService.newAttemptForUser(attempt)).willReturn(new GameService.GameResult(10, List.of()));

        //When
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAttemptRequest.write(attempt).getJson()))
                .andReturn().getResponse();

        //Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    public void PostInvalidAttempt() throws Exception {
        //Given
        AttemptVerifiedEvent attempt = new AttemptVerifiedEvent(1L, true, 1, 100, 1L, "");

        //When
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonAttemptRequest.write(attempt).getJson()))
                .andReturn().getResponse();

        //Then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

}
