package hyve.petshow.controller.representation;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ClienteRepresentation extends ContaRepresentation {
	private List<AnimalEstimacaoRepresentation> animaisEstimacao = new ArrayList<AnimalEstimacaoRepresentation>();
	private List<AvaliacaoRepresentation> avaliacoes = new ArrayList<AvaliacaoRepresentation>();
}
