package hyve.petshow.domain;

import java.util.Optional;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private CriteriosAvaliacao criteriosAvaliacao;
	@Column(name = "fk_servico_detalhado")
	private Long servicoAvaliadoId;
	@Column(name = "fk_conta")
	private Long clienteId;
	
	@Transient
	public Double getMediaAvaliacao() {
		return Optional.ofNullable(criteriosAvaliacao).orElse(new CriteriosAvaliacao()).getMediaAvaliacao();
	}}