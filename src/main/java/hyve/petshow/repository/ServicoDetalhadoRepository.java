package hyve.petshow.repository;

import hyve.petshow.domain.ServicoDetalhado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicoDetalhadoRepository extends JpaRepository<ServicoDetalhado, Long> {
//	@Query("select s from servicoDetalhado s where s.prestador.id = ?1")
//	List<ServicoDetalhado> findByPrestador(Long Id);
	@Query("select s from servico_detalhado s left join fetch s.avaliacoes where s.id = ?1")
	Optional<ServicoDetalhado> findById(Long id);
}