package hyve.petshow.domain;

import hyve.petshow.domain.embeddables.Auditoria;
import hyve.petshow.domain.embeddables.Endereco;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal precoFinal;
    private Float mediaAvaliacao;
    private LocalDate data;
    private String comentario;
    private Endereco endereco;
    private Auditoria auditoria;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "fk_animal_estimacao"))
    private List<AnimalEstimacao> animaisAtendidos;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "fk_servico_detalhado"))
    private List<ServicoDetalhado> servicosDetalhados;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_agendamento")
    private StatusAgendamento status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_prestador")
    private Prestador prestador;
}
