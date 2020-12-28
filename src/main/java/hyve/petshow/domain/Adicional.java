package hyve.petshow.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import hyve.petshow.domain.embeddables.Auditoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Adicional {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@EqualsAndHashCode.Exclude
	private String nome;
	@EqualsAndHashCode.Exclude
	private String descricao;
	//@EqualsAndHashCode.Exclude
	private BigDecimal preco;
	@Column(name = "fk_servico")
	//@EqualsAndHashCode.Exclude
	private Long idServicoDetalhado;
	@Embedded
	@EqualsAndHashCode.Exclude
	private Auditoria auditoria;
}
