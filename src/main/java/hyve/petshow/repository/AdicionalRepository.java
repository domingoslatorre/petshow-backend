package hyve.petshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hyve.petshow.domain.Adicional;

public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
	@Query("select a from Adicional a where a.idServicoDetalhado = ?1")
	List<Adicional> findByServicoDetalhado(Long idServico);
}
