package hyve.petshow.repository;

import hyve.petshow.domain.Avaliacao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	List<Avaliacao> findByServicoAvaliadoId(Long id, Pageable pageable);
}
