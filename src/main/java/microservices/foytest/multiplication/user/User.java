package microservices.foytest.multiplication.user;

import lombok.*;

/**
 * This class represents and identifies the user
 * It uses lombok to produce all the boilerplate required via annotations
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private Long id;
    private String alias;
}
