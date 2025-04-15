package telran.order_service.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

	@Bean
     Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder.deserializerByType(java.time.LocalDateTime.class, new MultiFormatDateTimeDeserializer());
    }
}
