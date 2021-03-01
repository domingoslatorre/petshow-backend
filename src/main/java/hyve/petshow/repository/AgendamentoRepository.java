package hyve.petshow.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hyve.petshow.domain.Agendamento;

import static hyve.petshow.util.AuditoriaUtils.ATIVO;

@Transactional
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    @Query("select a from agendamento a where a.cliente.id = ?1 and a.auditoria.flagAtivo = ?2")
    Page<Agendamento> findByClienteIdAndAuditoriaFlagAtivo(Long id, String flagAtivo, Pageable pageable);

    @Query("select a from agendamento a where a.prestador.id = ?1 and a.auditoria.flagAtivo = ?2")
    Page<Agendamento> findByPrestadorIdAndAuditoriaFlagAtivo(Long id, String flagAtivo, Pageable pageable);
    
    @Query("select a from agendamento a where a.prestador.id = ?1 and a.data >= ?2 and a.data <= ?3 and a.auditoria.flagAtivo = ?4")
    List<Agendamento> findAllByPrestadorAndDateAndAuditoriaFlagAtivo(Long prestadorId, LocalDateTime inicioDia, LocalDateTime fimDia, String flagAtivo);

    Optional<Agendamento> findByIdAndAuditoriaFlagAtivo(Long id, String flagAtivo);

    default List<Agendamento> findAllByPrestadorAndDateAndAuditoriaFlagAtivo(Long prestdadorId, LocalDate dataAgendamento) {
    	return findAllByPrestadorAndDateAndAuditoriaFlagAtivo(prestdadorId, dataAgendamento.atStartOfDay(), dataAgendamento.atTime(19, 0, 0), ATIVO);
    }


}
