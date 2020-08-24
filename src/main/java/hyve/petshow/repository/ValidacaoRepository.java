package hyve.petshow.repository;

import hyve.petshow.domain.Validacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacaoRepository extends CrudRepository<Validacao, Long> {
}
