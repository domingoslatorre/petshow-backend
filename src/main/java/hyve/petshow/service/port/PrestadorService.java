package hyve.petshow.service.port;

import org.springframework.stereotype.Service;

import hyve.petshow.domain.Cliente;

@Service
public interface PrestadorService extends ContaService<Cliente>{ //é somente uma interface


}
