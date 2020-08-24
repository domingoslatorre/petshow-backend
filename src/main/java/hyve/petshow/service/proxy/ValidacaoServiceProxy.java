package hyve.petshow.service.proxy;

import hyve.petshow.domain.Validacao;
import hyve.petshow.service.implementation.ValidacaoServiceImpl;
import hyve.petshow.service.port.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoServiceProxy implements ValidacaoService {
    @Autowired
    private ValidacaoServiceImpl validacaoService;

    @Override
    public List<Validacao> obterValidacoes() {
        return validacaoService.obterValidacoes();
    }

    @Override
    public Validacao obterValidacao(Long id) {
        return validacaoService.obterValidacao(id);
    }
}
