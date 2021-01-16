package hyve.petshow.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import hyve.petshow.domain.embeddables.Auditoria;
import hyve.petshow.domain.embeddables.Endereco;
import lombok.Data;

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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_agendamento")
    private Avaliacao avaliacao;
}
