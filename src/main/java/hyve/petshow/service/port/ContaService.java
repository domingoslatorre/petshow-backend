package hyve.petshow.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;

@Service
public interface ContaService {
	Conta salvaConta(Conta conta);
	Optional<Conta> obterContaPorId(Long id);
	List<Conta> obterContas();	
	Optional<Conta> obterPorLogin(Login login);
}
