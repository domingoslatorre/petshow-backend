package hyve.petshow.repository;

import hyve.petshow.domain.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	Page<Avaliacao> findByServicoAvaliadoId(Long id, Pageable pageable);

	Optional<Avaliacao> findByAgendamentoAvaliadoId(Long id);

	@Query("select (avg(a.criteriosAvaliacao.atencao) + " +
					"avg(a.criteriosAvaliacao.qualidadeProdutos) + " +
					"avg(a.criteriosAvaliacao.custoBeneficio) + " +
					"avg(a.criteriosAvaliacao.infraestrutura) + " +
					"avg(a.criteriosAvaliacao.qualidadeServico)) / 5.0 as mediaAgendamento " +
			"from Avaliacao a " +
			"left join a.agendamentoAvaliado b " +
			"where b.servicoDetalhado.id =  ?1")
	Float findMediaAvaliacaoByServicoDetalhado(Long id);
}
