package microservices.foytest.multiplication.challenge.controllers;

import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.challenge.dto.ChallengeAttemptDTO;
import microservices.foytest.multiplication.challenge.services.ChallengeService;
import microservices.foytest.multiplication.user.domain.User;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
public class ChallengeAttemptControllerTest {
    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;
    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResultAttempt;

    @Autowired
    private JacksonTester<List<ChallengeAttempt>> jsonAttemptList;

    @Test
    void postRightAnswer() throws Exception {
        //given
        User user = new User(1L, "Foy");
        long attemptId = 1L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 70, "Foy", 3500);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(attemptId, user, 50, 70, 3500, true);
        given(challengeService.verifyAttempt(eq(attemptDTO))).willReturn(expectedResponse);

        //When
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();

        //Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonResultAttempt.write(expectedResponse).getJson());
    }

    @Test
    void postWrongAnswer() throws Exception {
        //given
        User user = new User(1L, "Foy");
        long attemptId = 1L;
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 70, "Foy", 5500);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(attemptId, user, 50, 70, 5500, false);
        given(challengeService.verifyAttempt(eq(attemptDTO))).willReturn(expectedResponse);

        //When
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();

        //Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonResultAttempt.write(expectedResponse).getJson());
    }

    @Test
    void postInvalidData() throws Exception {
        //given an attempt with invalid input data
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(2000, -70, "foy", 1);

        //when
        MockHttpServletResponse response = mvc.perform(
                        post("/attempts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void getAttemptsForUser() throws Exception {
        //Given a valid Alias
        User user = new User(1L, "Foy");
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 20, 30, 5000, true);
        ChallengeAttempt attempt2 = new ChallengeAttempt(1L, user, 20, 30, 5000, true);
        List<ChallengeAttempt> expectedAttemptList = List.of(attempt1, attempt2);
        given(challengeService.getAttemptsForUser(user.getAlias())).willReturn(expectedAttemptList);

        //when
        MockHttpServletResponse response = mvc.perform(
                get("/attempts?alias=Foy")).andReturn().getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonAttemptList.write(expectedAttemptList)
                        .getJson());
    }
}
