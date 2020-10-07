package hyve.petshow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Login;

@Transactional
public interface ClienteRepository extends ContaRepository<Cliente> {
	@Query("select c from Cliente c left join fetch c.animaisEstimacao where c.id = ?1")
	Optional<Cliente> findById(Long id);
}
