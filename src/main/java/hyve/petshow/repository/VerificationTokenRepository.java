package hyve.petshow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hyve.petshow.domain.Conta;
import hyve.petshow.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByToken(String token);
	 
    Optional<VerificationToken> findByConta(Conta conta);
}
