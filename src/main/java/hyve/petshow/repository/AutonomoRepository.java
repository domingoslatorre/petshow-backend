package hyve.petshow.repository;

import hyve.petshow.domain.Autonomo;
import hyve.petshow.domain.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AutonomoRepository extends ContaRepository<Autonomo> {
    Optional<Autonomo> findByLogin(Login login);
}
