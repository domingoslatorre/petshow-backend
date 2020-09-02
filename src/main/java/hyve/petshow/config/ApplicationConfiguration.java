package hyve.petshow.config;

import hyve.petshow.service.implementation.AnimalEstimacaoServiceImpl;
import hyve.petshow.service.port.AnimalEstimacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public AnimalEstimacaoService animalEstimacaoService(){
        return new AnimalEstimacaoServiceImpl();
    }
}
