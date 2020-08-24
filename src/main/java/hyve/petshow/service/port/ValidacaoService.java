package hyve.petshow.service.port;

import hyve.petshow.domain.Validacao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ValidacaoService {
    List<Validacao> obterValidacoes();
    Validacao obterValidacao(Long id);
}
