package hyve.petshow.repository;

import javax.transaction.Transactional;

import hyve.petshow.domain.Conta;

@Transactional
public interface GenericContaRepository extends ContaRepository<Conta>{

}
