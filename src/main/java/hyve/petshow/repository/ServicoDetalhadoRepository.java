package hyve.petshow.repository;

import hyve.petshow.domain.ServicoDetalhado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ServicoDetalhadoRepository extends JpaRepository<ServicoDetalhado, Long> {
	//tentativa de query para selecionar todos os serviços detalhados de um prestador
	//@Query("select servicosDetalhados from prestador p where p.id = ?1")
	List<ServicoDetalhado> findByPrestador(Long Id);
	Optional<ServicoDetalhado> findById (Long Id);
}



