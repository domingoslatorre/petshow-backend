package hyve.petshow.service.port;

import hyve.petshow.domain.Validacao;
import hyve.petshow.service.BusinessService;
import org.springframework.stereotype.Service;

@Service
public interface ValidacaoService extends BusinessService<Validacao, Long> {
}
