package microservices.foytest.multiplication.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.foytest.multiplication.user.data.UserRepository;
import microservices.foytest.multiplication.user.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    // here we use the repository directly instead of the service to avoid adding complexity to the logic
    private final UserRepository userRepository;

    @GetMapping("/{ids}")
    //@PathVariable means that the {ids} parameter is a path variable expected as users/1,2,3
    public List<User> getUsersByIds(@PathVariable final List<Long> ids) {
        log.info("Getting users by ids: {}", ids);
        return userRepository.findAllByIdIn(ids);
    }
}
