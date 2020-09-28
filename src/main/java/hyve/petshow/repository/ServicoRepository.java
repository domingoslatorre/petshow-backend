package hyve.petshow.repository;

import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
	Optional<Servico> findByNome(String nome);
}
