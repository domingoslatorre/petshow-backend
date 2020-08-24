package hyve.petshow.config;

import hyve.petshow.facade.port.TesteValidacaoFacade;
import hyve.petshow.facade.proxy.TesteValidacaoFacadeProxy;
import hyve.petshow.service.port.TesteService;
import hyve.petshow.service.port.ValidacaoService;
import hyve.petshow.service.proxy.TesteServiceProxy;
import hyve.petshow.service.proxy.ValidacaoServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public TesteService testeService(){
        return new TesteServiceProxy();
    }

    @Bean
    public ValidacaoService validacaoService(){
        return new ValidacaoServiceProxy();
    }

    @Bean
    public TesteValidacaoFacade testeValidacaoFacade(){
        return new TesteValidacaoFacadeProxy();
    }
}
