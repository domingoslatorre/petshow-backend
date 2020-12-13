package hyve.petshow.controller.representation;

import hyve.petshow.domain.embeddables.Endereco;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AgendamentoRepresentation {
    private Long id;
    private BigDecimal precoFinal;
    private Float mediaAvaliacao;
    private LocalDate data;
    private String comentario;
    private Endereco endereco;
    private List<AnimalEstimacaoRepresentation> animaisAtendidos;
    private List<ServicoDetalhadoRepresentation> servicosDetalhados;
    private Integer statusId;
    private StatusAgendamentoRepresentation status;
    private Long clienteId;
    private ClienteRepresentation cliente;
    private Long prestadorId;
    private PrestadorRepresentation prestador;
}
