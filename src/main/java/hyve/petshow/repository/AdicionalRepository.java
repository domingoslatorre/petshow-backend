package hyve.petshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hyve.petshow.domain.Adicional;

public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
	List<Adicional> findByServicoDetalhadoId(Long idServico);
}
