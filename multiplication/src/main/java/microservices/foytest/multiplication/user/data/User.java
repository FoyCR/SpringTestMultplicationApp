package microservices.foytest.multiplication.user.data;

import jakarta.persistence.*;
import lombok.*;

/**
 * This class represents and identifies the user
 * It uses lombok to produce all the boilerplate required via annotations
 */
@Entity(name = "users") //mark this class as an object to be mapped to a database table called user
@Data //This annotation groups equals and hashCode methods, toString, getters, and setters
@AllArgsConstructor
@NoArgsConstructor //PA and Hibernate also require your entities to have a default, empty constructor
public class User {
    @Id   //id row in the database
    @GeneratedValue //auto generate value for the database
    private Long id;
    private String alias;

    public User(final String userAlias) {
        this(null, userAlias);
    }
}