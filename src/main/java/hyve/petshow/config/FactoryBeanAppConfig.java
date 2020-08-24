package hyve.petshow.config;

import hyve.petshow.factory.TesteFactory;
import hyve.petshow.domain.Teste;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanAppConfig {
    @Bean(name = "teste")
    public TesteFactory testeFactory() {
        TesteFactory factory = new TesteFactory();
        return factory;
    }

    @Bean
    public Teste teste() throws Exception {
        return testeFactory().getObject();
    }
}
