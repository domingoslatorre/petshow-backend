package hyve.petshow.controller.representation;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClienteRepresentation extends ContaRepresentation {
	private List<AnimalEstimacaoRepresentation> animaisEstimacao = new ArrayList<AnimalEstimacaoRepresentation>();
}
