package hyve.petshow.facade.implementation;

import hyve.petshow.domain.Teste;
import hyve.petshow.domain.TesteValidacao;
import hyve.petshow.domain.Validacao;
import hyve.petshow.facade.port.TesteValidacaoFacade;
import hyve.petshow.service.port.TesteService;
import hyve.petshow.service.port.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TesteValidacaoFacadeImpl implements TesteValidacaoFacade {
    @Autowired
    private TesteService testeService;

    @Autowired
    private ValidacaoService validacaoService;

    @Override
    public TesteValidacao obterTesteValidacao(Long idTeste, Long idValidacao) {
        TesteValidacao testeValidacao = new TesteValidacao();
        Teste teste = testeService.obterTeste(idTeste);
        Validacao validacao = validacaoService.obterUmPorId(idValidacao).get();

        testeValidacao.setIdTeste(teste.getId());
        testeValidacao.setIdValidacao(validacao.getId());
        testeValidacao.setTeste(teste.getTeste());
        testeValidacao.setValidacao(validacao.getValidacao());

        return testeValidacao;
    }
}
