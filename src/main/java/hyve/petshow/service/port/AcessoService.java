package hyve.petshow.service.port;

import hyve.petshow.domain.Conta;
import hyve.petshow.exceptions.BusinessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AcessoService extends UserDetailsService {
    Conta adicionarConta(Conta conta) throws BusinessException;

    Optional<Conta> buscarPorEmail(String email);
}
