package microservices.foytest.multiplication;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// This apply Hibernate5JakartaModule to avoid serialize errors with empty Bean without force the retrieve from db
public class JsonConfiguration {
    @Bean
    public Module hibernateModule() {
        return new Hibernate5JakartaModule();
    }

}
