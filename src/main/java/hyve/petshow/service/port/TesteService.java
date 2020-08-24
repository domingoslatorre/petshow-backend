package hyve.petshow.service.port;

import hyve.petshow.domain.Teste;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TesteService {
    List<Teste> obterTestes();
    Teste obterTeste(Long id);
}
