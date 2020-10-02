package hyve.petshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.ServicoDetalhado;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	@Query("select a from Avaliacao a join fetch a.cliente c left join fetch c.animaisEstimacao where a.servicoAvaliado = ?1")
	List<Avaliacao> findByServicoAvaliado(ServicoDetalhado servicoAvaliado);
}
