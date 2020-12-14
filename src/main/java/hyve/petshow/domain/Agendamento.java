package hyve.petshow.domain;

import hyve.petshow.domain.embeddables.Auditoria;
import hyve.petshow.domain.embeddables.Endereco;
import lombok.Data;
import org.springframework.objenesis.instantiator.annotations.Instantiator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal precoFinal;
    private Float mediaAvaliacao;
    private LocalDateTime data;
    private String comentario;
    private Endereco endereco;
    private Auditoria auditoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_agendamento")
    private StatusAgendamento status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_prestador")
    private Prestador prestador;
    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private List<AnimalEstimacaoAgendamento> animaisAtendidos = new ArrayList<>();
    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private List<ServicoDetalhadoAgendamento> servicosPrestados = new ArrayList<>();
}
