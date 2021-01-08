package hyve.petshow.controller.representation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServicoDetalhadoRepresentation {
	private Long Id;
	private ServicoRepresentation tipo;
	private List<AvaliacaoRepresentation> avaliacoes;
	private Float mediaAvaliacao;
	private Long prestadorId;
	private PrestadorRepresentation prestador;
	private List<PrecoPorTipoRepresentation> precoPorTipo = new ArrayList<>();
	private List<AdicionalRepresentation> adicionais;
}
