package hyve.petshow.repository;

import hyve.petshow.domain.ServicoDetalhado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicoDetalhadoRepository extends JpaRepository<ServicoDetalhado, Long> {
//	@Query("select s from servicoDetalhado s where s.prestador.id = ?1")
//	List<ServicoDetalhado> findByPrestador(Long Id);

}