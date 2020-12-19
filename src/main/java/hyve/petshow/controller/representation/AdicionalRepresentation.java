package hyve.petshow.controller.representation;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AdicionalRepresentation {
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Long idServicoDetalhado;
}
