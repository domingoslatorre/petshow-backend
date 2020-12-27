package hyve.petshow.controller.representation;

import lombok.Data;

import java.math.BigDecimal;
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
<<<<<<< HEAD
	private List<PrecoPorTipoRepresentation> precoPorTipo = new ArrayList<>();
=======
	private List<AdicionalRepresentation> adicionais;
>>>>>>> 205f6d1885fb0d324b30f2dcbf89d541b8164383
}
