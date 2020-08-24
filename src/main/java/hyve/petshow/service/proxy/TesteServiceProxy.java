package hyve.petshow.service.proxy;

import hyve.petshow.domain.Teste;
import hyve.petshow.service.implementation.TesteServiceImpl;
import hyve.petshow.service.port.TesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TesteServiceProxy implements TesteService {
    @Autowired
    private TesteServiceImpl testeService;

    @Override
    public List<Teste> obterTestes() {
        return testeService.obterTestes();
    }

    @Override
    public Teste obterTeste(Long id) {
        return testeService.obterTeste(id);
    }
}
