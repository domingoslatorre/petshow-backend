package hyve.petshow.domain;

import java.util.Optional;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private AvaliacaoInfo avaliacaoInfo;
	@ManyToOne
	@JoinColumn(name = "fk_servico", referencedColumnName = "id")
	private ServicoDetalhado servicoAvaliado;
	@ManyToOne
	@JoinColumn(name = "fk_conta", referencedColumnName = "id")
	private Cliente cliente;
	
	@Transient
	public Double getMediaAvaliacao() {
		return Optional.ofNullable(avaliacaoInfo).orElse(new AvaliacaoInfo()).getMediaAvaliacao();
	}}
