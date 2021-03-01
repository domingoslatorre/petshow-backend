package hyve.petshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hyve.petshow.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
