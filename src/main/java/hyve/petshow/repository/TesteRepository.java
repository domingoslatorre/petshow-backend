package hyve.petshow.repository;

import hyve.petshow.domain.Teste;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteRepository extends CrudRepository<Teste, Long> {
}
