package hyve.petshow.facade.proxy;

import hyve.petshow.domain.TesteValidacao;
import hyve.petshow.facade.implementation.TesteValidacaoFacadeImpl;
import hyve.petshow.facade.port.TesteValidacaoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TesteValidacaoFacadeProxy implements TesteValidacaoFacade {
    @Autowired
    private TesteValidacaoFacadeImpl testeValidacaoFacade;

    @Override
    public TesteValidacao obterTesteValidacao(Long idTeste, Long idValidacao) {
        return testeValidacaoFacade.obterTesteValidacao(idTeste, idValidacao);
    }
}
