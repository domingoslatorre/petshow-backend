package hyve.petshow.configuration;

import hyve.petshow.service.implementation.AnimalEstimacaoServiceImpl;
import hyve.petshow.service.implementation.ClienteServiceImpl;
import hyve.petshow.service.port.AnimalEstimacaoService;
import hyve.petshow.service.port.ContaService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public AnimalEstimacaoService animalEstimacaoService(){
        return new AnimalEstimacaoServiceImpl();
    }
    
//    @Bean
//    public ContaService contaService() {
//    	return new ContaServiceImpl();
//    }
}
