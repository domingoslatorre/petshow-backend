package hyve.petshow.repository;

import java.util.Optional;

import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PrestadorRepository extends ContaRepository<Prestador> {
    Optional<Prestador> findByLogin(Login login);
}
