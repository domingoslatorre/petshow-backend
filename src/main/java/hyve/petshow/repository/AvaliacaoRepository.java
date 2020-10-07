package hyve.petshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.ServicoDetalhado;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	List<Avaliacao> findByServicoAvaliadoId(Long id);
}
