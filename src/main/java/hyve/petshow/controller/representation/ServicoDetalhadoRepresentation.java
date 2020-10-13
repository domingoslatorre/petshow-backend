package hyve.petshow.controller.representation;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ServicoDetalhadoRepresentation {
	private Long Id;
	private BigDecimal preco;
	private ServicoRepresentation tipo;
	private List<AvaliacaoRepresentation> avaliacoes;
    private Long prestadorId;
}
