package hyve.petshow.repository;

import hyve.petshow.domain.TipoAnimalEstimacao;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable
public interface TipoAnimalEstimacaoRepository extends JpaRepository<TipoAnimalEstimacao, Integer> {
}
