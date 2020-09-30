package hyve.petshow.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Embeddable
public class AvaliacaoInfo {
	private Integer atencao;
	private Integer qualidadeProdutos;
	private Integer custoBeneficio;
	private Integer infraestrutura;
	private Integer qualidadeServico;
	private String comentario;

	@Transient
	public Double getMediaAvaliacao() {
		List<Integer> valoresAvaliacao = Arrays.asList(atencao, qualidadeProdutos, custoBeneficio, infraestrutura,
				qualidadeServico);

		return valoresAvaliacao.stream().mapToDouble(el -> el == null ? 0 : el).sum() / valoresAvaliacao.size();
	}
}
