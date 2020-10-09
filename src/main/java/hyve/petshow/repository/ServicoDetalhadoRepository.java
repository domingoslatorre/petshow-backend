package hyve.petshow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hyve.petshow.domain.ServicoDetalhado;


@Repository
public interface ServicoDetalhadoRepository extends JpaRepository<ServicoDetalhado, Long> {
	@Query("select s from servico_detalhado s left join fetch s.avaliacoes where s.tipo.id=?1")
	List<ServicoDetalhado> findByTipo(Integer id);
	
	@Query("select s from servico_detalhado s left join fetch s.avaliacoes where s.id=?1")
	Optional<ServicoDetalhado> findById(Long id);

	@Query("select s from servico_detalhado s left join fetch s.avaliacoes where s.prestadorId=?1")
	List<ServicoDetalhado> findByPrestadorId(Long prestadorId);
	
	@Query("select s from servico_detalhado s left join fetch s.avaliacoes where s.id=?1 and s.prestadorId=?2")
	Optional<ServicoDetalhado> findByIdAndPrestadorId(Long idServico, Long idPrestador);
}