package hyve.petshow.domain;

import java.util.Optional;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private CriteriosAvaliacao criteriosAvaliacao;
	@ManyToOne
	private ServicoDetalhado servicoAvaliado;
	@ManyToOne
	private Cliente cliente;
	
	@Transient
	public Double getMediaAvaliacao() {
		return Optional.ofNullable(criteriosAvaliacao).orElse(new CriteriosAvaliacao()).getMediaAvaliacao();
	}}
