package microservices.foytest.multiplication.challenge;

import lombok.*;
import microservices.foytest.multiplication.user.User;
import jakarta.persistence.*;


/**
 * Identifies the attempt from a {@link User} to solve a Challenge
 * It stored the factors of the challenge, because the challenge is created 'on the fly'
 * It uses lombok to produce all the boilerplate required via annotations
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeAttempt {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //one use can have many attempts. If you set it to EAGER, the user data gets
    // collected with the attempt. With LAZY, the query to retrieve those fields will be executed only when you try to
    // access them
    @JoinColumn(name = "USER_ID") //This annotation makes Hibernate link both tables with a join column, It will create
    // a new column in the CHALLENGE_ATTEMPT table called USER_ID which will store the reference to the ID record of the
    // corresponding user in the USER table.
    private User user; //embedded entity type: users

    private int factorA;
    private int factorB;
    private int answerAttempt;
    private boolean isCorrect;
}
