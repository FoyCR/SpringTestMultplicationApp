package microservices.foytest.multiplication.challenge;

import lombok.*;
import microservices.foytest.multiplication.user.User;

/**
 * Identifies the attempt from a {@link User} to solve a Challenge
 * It stored the factors of the challenge, because the challenge is created 'on the fly'
 * It uses lombok to produce all the boilerplate required via annotations
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ChallengeAttempt {
    private Long id;
    private Long userId;
    private int factorA;
    private int factorB;
    private int answerAttempt;
    private boolean isCorrect;
}
