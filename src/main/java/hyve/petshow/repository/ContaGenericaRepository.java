package hyve.petshow.repository;

import hyve.petshow.domain.Conta;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface ContaGenericaRepository extends ContaRepository<Conta>{

}
