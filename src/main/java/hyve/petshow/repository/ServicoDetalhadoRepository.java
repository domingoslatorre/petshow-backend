package hyve.petshow.repository;

import hyve.petshow.domain.ServicoDetalhado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ServicoDetalhadoRepository extends JpaRepository<ServicoDetalhado, Long> {
	@Query("select s from servico_detalhado s left join fetch s.avaliacoes where s.id=?1")
	Optional<ServicoDetalhado> findById(Long id);

	@Query("select s from servico_detalhado s where s.prestadorId=?1")
	Page<ServicoDetalhado> findByPrestadorId(Long prestadorId, Pageable pageable);
	
	@Query("select s from servico_detalhado s left join fetch s.avaliacoes where s.id=?1 and s.prestadorId=?2")
	Optional<ServicoDetalhado> findByIdAndPrestadorId(Long idServico, Long idPrestador);

	@Query("select s from servico_detalhado s where s.tipo.id=?1")
	Page<ServicoDetalhado> findByTipo(Integer id, Pageable pageable);
}