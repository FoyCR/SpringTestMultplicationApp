package microservices.foy.gamification.controllers;

import microservices.foy.gamification.game.controllers.LeaderBoardController;
import microservices.foy.gamification.game.domain.LeaderBoardRow;
import microservices.foy.gamification.game.services.LeaderBoardService;
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

import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(LeaderBoardController.class)
public class LeaderBoardControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LeaderBoardService leaderBoardService;

    @Autowired
    private JacksonTester<List<LeaderBoardRow>> jsonLeaderBoard;

    @Test
    public void shouldReturnLeaderBoard() throws Exception {
        //given
        LeaderBoardRow row1 = new LeaderBoardRow(1L, 300L);
        LeaderBoardRow row2 = new LeaderBoardRow(2L, 200L);
        List<LeaderBoardRow> leaderBoard = new ArrayList<>();
        Collections.addAll(leaderBoard, row1, row2);
        given(leaderBoardService.getCurrentLeaderBoard()).willReturn(leaderBoard);

        //when
        MockHttpServletResponse response = mvc.perform(
                        get("/leaders")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonLeaderBoard.write(leaderBoard).getJson());

    }

}
