package hyve.petshow.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hyve.petshow.domain.Agendamento;

@Transactional
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    @Query("select a from agendamento a where a.cliente.id = ?1")
    Page<Agendamento> findByClienteId(Long id, Pageable pageable);

    @Query("select a from agendamento a where a.prestador.id = ?1")
    Page<Agendamento> findByPrestadorId(Long id, Pageable pageable);
    
    @Query("select a from agendamento a where a.prestador.id = ?1 and a.data >= ?2 and a.data <= ?3")
    List<Agendamento> findAllByPrestadorAndDate(Long prestadorId, LocalDateTime inicioDia, LocalDateTime fimDia);
    
    default List<Agendamento> findAllByPrestadorAndDate(Long prestdadorId, LocalDate dataAgendamento) {
    	return findAllByPrestadorAndDate(prestdadorId, dataAgendamento.atStartOfDay(), dataAgendamento.atTime(19, 0, 0));
    }
    
    
}
