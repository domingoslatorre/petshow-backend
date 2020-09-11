package hyve.petshow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;

public interface ContaRepository<T extends Conta> extends JpaRepository<T, Long> {
	Optional<T> findByLogin(Login login);
	Optional<T> findByCpf(String cpf);
	
	@Query("select c from conta c where c.login.email = ?1")
	Optional<Conta> findByEmail(String email);
}
