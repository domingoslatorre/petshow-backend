package hyve.petshow.controller.representation;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdicionalRepresentation {
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Long idServicoDetalhado;
}
