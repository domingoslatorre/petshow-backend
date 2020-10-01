package hyve.petshow.controller.representation;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ServicoDetalhadoRepresentation {
	private Long Id;
	private BigDecimal preco;
	private ServicoRepresentation tipo;
	private List<AvaliacaoRepresentation> avaliacoes;
//    private List<TipoAnimalEstimacao> animaisAceitos;
//    private PrestadorRepresentation prestador;
}
