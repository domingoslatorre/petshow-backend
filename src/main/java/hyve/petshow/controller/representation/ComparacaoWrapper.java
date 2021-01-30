package hyve.petshow.controller.representation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparacaoWrapper {
	private List<ComparacaoRepresentation> servicosComparados = new ArrayList<>();
	private Map<String, Long> comparacoes = new HashMap<String, Long>();
}
