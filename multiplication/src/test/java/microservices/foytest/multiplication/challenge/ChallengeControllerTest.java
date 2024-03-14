package microservices.foytest.multiplication.challenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.json.JacksonTester;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class) //ensure that JUnit5 test loads the extensions for Spring
@AutoConfigureJsonTesters //Serialize and deserialize objects
@WebMvcTest(ChallengeController.class)
//with the controller class as a parameter, makes Spring treat this as a presentation layer test
public class ChallengeControllerTest {
    @MockBean // replace the service bean in the context by a mock.
    private ChallengeGeneratorService challengeGeneratorService;

    @Autowired // make it inject (or wire) a bean in the context to the field
    private MockMvc mvc; // is used in Spring to simulate requests to the presentation layer when you make a test that does not load a real server

    @Autowired
    private JacksonTester<Challenge> jsonChallenge;

    @Test
    void getRandomChallenge() throws Exception {
        // given
        Challenge expectedChallenge = new Challenge(30, 60);
        given(challengeGeneratorService.randomChallenge()).willReturn(expectedChallenge);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/challenges/random")).andReturn().getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(
                jsonChallenge.write(expectedChallenge)
                        .getJson());

    }
}
