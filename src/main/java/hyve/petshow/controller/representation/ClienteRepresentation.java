package hyve.petshow.controller.representation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClienteRepresentation extends ContaRepresentation {
	private List<AnimalEstimacaoRepresentation> animaisEstimacao = new ArrayList<AnimalEstimacaoRepresentation>();
}
