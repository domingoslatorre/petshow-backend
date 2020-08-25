package hyve.petshow.service.proxy;

import hyve.petshow.domain.Validacao;
import hyve.petshow.service.implementation.ValidacaoServiceImpl;
import hyve.petshow.service.port.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidacaoServiceProxy implements ValidacaoService {
    @Autowired
    private ValidacaoServiceImpl validacaoServiceImpl;

    @Override
    public Validacao criar(Validacao validacao) {
        return validacaoServiceImpl.criar(validacao);
    }

    @Override
    public Validacao atualizar(Validacao validacao) {
        return validacaoServiceImpl.atualizar(validacao);
    }

    @Override
    public Boolean remover(Long id) {
        return validacaoServiceImpl.remover(id);
    }

    @Override
    public Optional<Validacao> obterUmPorId(Long id) {
        return validacaoServiceImpl.obterUmPorId(id);
    }

    @Override
    public Iterable<Validacao> obterTodos() {
        return validacaoServiceImpl.obterTodos();
    }
}
