package pl.archala.ideal.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class BeanConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

}
