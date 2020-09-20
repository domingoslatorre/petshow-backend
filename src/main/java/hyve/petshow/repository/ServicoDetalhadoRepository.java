package hyve.petshow.repository;

import hyve.petshow.domain.ServicoDetalhado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicoDetalhadoRepository extends JpaRepository<ServicoDetalhado, Long> {
	@Query("select servicoDetalhado from prestador p where p.id = ?1")
	List<ServicoDetalhado> findByPrestador(Long Id);
}



