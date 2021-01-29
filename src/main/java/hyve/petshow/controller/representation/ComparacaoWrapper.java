package hyve.petshow.controller.representation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class ComparacaoWrapper {
	List<ComparacaoRepresentation> comparacoes = new ArrayList<>();	
	public ComparacaoWrapper() {}
	
	
	
	public ComparacaoWrapper(List<ServicoDetalhadoRepresentation> servicos) {
		this.setComparacoes(servicos.stream().map(el -> new ComparacaoRepresentation(el)).collect(Collectors.toList()));
	}
}
