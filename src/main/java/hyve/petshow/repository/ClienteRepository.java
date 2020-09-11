package hyve.petshow.repository;

import org.springframework.transaction.annotation.Transactional;

import hyve.petshow.domain.Cliente;

@Transactional
public interface ClienteRepository extends ContaRepository<Cliente>{

}
