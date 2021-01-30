package hyve.petshow.controller.representation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparacaoRepresentation {
	private Long id;
	private String nome;
	private BigDecimal menorPreco;
	private BigDecimal maiorPreco;
	private List<PrecoPorTipoRepresentation> tabelaPrecos;
	private BigDecimal mediaAvaliacao;
	private List<AdicionalRepresentation> adicionais;
	private Map<String, Boolean> tiposAtendidos;
}
