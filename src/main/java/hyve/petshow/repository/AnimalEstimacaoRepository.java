package hyve.petshow.repository;

import hyve.petshow.domain.AnimalEstimacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalEstimacaoRepository extends JpaRepository<AnimalEstimacao, Long> {
}
