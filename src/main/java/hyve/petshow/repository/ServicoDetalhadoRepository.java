package hyve.petshow.repository;

import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.Prestador;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicoDetalhadoRepository extends JpaRepository<ServicoDetalhado, Long> {
//	@Query("select s from servicoDetalhado s where s.prestador.id = ?1")
//	List<ServicoDetalhado> findByPrestador(Long Id);
@Query("select s from servicoDetalhado s where s.tipo.id=?1")
List<ServicoDetalhado> findByTipo (Long id);
}