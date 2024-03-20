package microservices.foytest.multiplication.user.controllers;

import microservices.foytest.multiplication.user.domain.User;
import microservices.foytest.multiplication.user.data.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.BDDAssertions.then;


import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    JacksonTester<List<User>> jsonUsers;

    @Test
    public void shouldReturnAListOfUsersByIds() throws Exception {
        // given
        User user1 = new User(1L, "Foy");
        User user2 = new User(2L, "Nata");
        List<User> users = List.of(user1, user2);
        given(userRepository.findAllByIdIn(List.of(1L, 2L))).willReturn(users);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/users/1,2"))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(200);
        then(response.getContentAsString()).isEqualTo(jsonUsers.write(users).getJson());

    }
}
