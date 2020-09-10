package hyve.petshow.controller.representation;

import java.util.List;

import lombok.Data;

@Data
public class ClienteRepresentation extends ContaRepresentation {
	private List<AnimalEstimacaoRepresentation> animaisEstimacao;
}
