package hyve.petshow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	Optional<Conta> findByLogin(Login login);
}
