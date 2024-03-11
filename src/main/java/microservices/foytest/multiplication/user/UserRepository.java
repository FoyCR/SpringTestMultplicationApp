package microservices.foytest.multiplication.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// You don’t need to implement these interfaces. You don’t even need to add the Spring’s @Repository annotation.
// Spring, using the Data module, will find interfaces extending the base ones and will inject beans that implement
// the desired behavior
public interface UserRepository extends CrudRepository<User, Long> {
    // Return a user wrapped in a Java Optional<T>
    Optional<User> finByAlias(final String alias);
}
