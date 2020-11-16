package hyve.petshow.domain;

import hyve.petshow.domain.embeddables.Auditoria;
import hyve.petshow.domain.embeddables.CriteriosAvaliacao;
import lombok.Data;

import javax.persistence.*;
import java.util.Optional;

@Data
@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private CriteriosAvaliacao criteriosAvaliacao;
	@Embedded
	private Auditoria auditoria;
	@Column(name = "fk_servico_detalhado")
	private Long servicoAvaliadoId;
	@Column(name = "fk_conta")
	private Long clienteId;
	
	@Transient
	public Double getMediaAvaliacao() {
		return Optional.ofNullable(criteriosAvaliacao).orElse(new CriteriosAvaliacao()).getMediaAvaliacao();
	}}
