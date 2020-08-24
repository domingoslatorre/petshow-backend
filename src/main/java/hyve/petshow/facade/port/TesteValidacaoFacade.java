package hyve.petshow.facade.port;

import hyve.petshow.domain.TesteValidacao;
import org.springframework.stereotype.Component;

@Component
public interface TesteValidacaoFacade {
    TesteValidacao obterTesteValidacao(Long idTeste, Long idValidacao);
}
