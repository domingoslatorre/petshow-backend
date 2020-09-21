package hyve.petshow.service.port;

import hyve.petshow.domain.Prestador;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Cliente;

@Service
public interface PrestadorService extends ContaService<Prestador>{ //é somente uma interface

}
