package hyve.petshow.repository;

import hyve.petshow.domain.AnimalEstimacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AnimalEstimacaoRepository extends JpaRepository<AnimalEstimacao, Long> {
    Optional<AnimalEstimacao> findByIdAndAuditoriaFlagAtivo(Long id, String flagAtivo);
    Page<AnimalEstimacao> findByDonoIdAndAuditoriaFlagAtivo(Long id, String flagAtivo, Pageable pageable);
    List<AnimalEstimacao> findByDonoIdAndIdInAndAuditoriaFlagAtivo(Long donoId, List<Long> animaisEstimacaoIds, String flagAtivo);
}
