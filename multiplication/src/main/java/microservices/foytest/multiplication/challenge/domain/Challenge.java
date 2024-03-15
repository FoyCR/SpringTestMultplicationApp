package microservices.foytest.multiplication.challenge.domain;

import lombok.*;

/**
 * This class represents a Challenge to solve a Multiplication (a * b)
 * It uses lombok to produce all the boilerplate required via annotations
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Challenge {
    private int factorA;
    private int factorB;
}
