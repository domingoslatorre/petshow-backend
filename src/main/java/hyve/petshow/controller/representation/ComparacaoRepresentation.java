package hyve.petshow.controller.representation;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ComparacaoRepresentation {
	private ServicoDetalhadoRepresentation representation = new ServicoDetalhadoRepresentation();
	
	public ComparacaoRepresentation() {}
	public ComparacaoRepresentation(ServicoDetalhadoRepresentation representation ) {
		setRepresentation(representation);
	}
	
	public void setRepresentation(ServicoDetalhadoRepresentation representation) {
		this.representation = representation;
	}
	
	public BigDecimal getMenorPreco() {
		return representation.getPrecoPorTipo()
				.stream()
				.min(Comparator.comparing(PrecoPorTipoRepresentation::getPreco))
				.map(el -> el.getPreco()).orElse(null);
	}

	public BigDecimal getMaiorPreco() {
		return representation.getPrecoPorTipo()
				.stream()
				.max(Comparator.comparing(PrecoPorTipoRepresentation::getPreco))
				.map(el -> el.getPreco()).orElse(null);
	}
	
	public BigDecimal getPrecoMedio() {
		return new BigDecimal(representation.getPrecoPorTipo()
				.stream()
				.map(el -> el.getPreco())
				.collect(Collectors.averagingDouble(el -> el.doubleValue()))).setScale(2);
				
	}
	
	public List<PrecoPorTipoRepresentation> getTabelaPrecos() {
		return representation.getPrecoPorTipo();
	}
	
	public BigDecimal getMediaAvaliacao(){
		return new BigDecimal(representation.getMedia()).setScale(2);
	}
	
	public List<AdicionalRepresentation> getListaAdicionais() {
		return representation.getAdicionais();
	}
	
	public Map<String, Boolean> getTiposAtendidos() {	
		return representation.getPrecoPorTipo()
				.stream()
				.map(el -> el.getTipoAnimal())
				.collect(Collectors.toMap(TipoAnimalEstimacaoRepresentation::getNome, el -> true));
	}
}
