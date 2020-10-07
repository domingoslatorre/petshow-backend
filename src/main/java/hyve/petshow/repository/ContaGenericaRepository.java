package hyve.petshow.repository;

import org.springframework.transaction.annotation.Transactional;

import hyve.petshow.domain.Conta;
@Transactional
public interface ContaGenericaRepository extends ContaRepository<Conta>{

}
