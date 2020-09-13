package hyve.petshow.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hyve.petshow.service.implementation.AnimalEstimacaoServiceImpl;
import hyve.petshow.service.port.AnimalEstimacaoService;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public AnimalEstimacaoService animalEstimacaoService(){
        return new AnimalEstimacaoServiceImpl();
    }
}
