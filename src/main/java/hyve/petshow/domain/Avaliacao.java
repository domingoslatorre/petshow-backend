package hyve.petshow.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer atencao;
	private Integer qualidadeProdutos;
	private Integer custoBeneficio;
	private Integer infraestrutura;
	private Integer qualidadeServico;
	private String comentario;
	@ManyToOne
	private ServicoDetalhado servicoAvaliado;
	@ManyToOne
	@JoinColumn(name = "fk_conta", referencedColumnName = "id")
	private Cliente cliente;

}
