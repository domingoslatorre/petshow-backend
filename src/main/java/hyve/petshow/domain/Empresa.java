package hyve.petshow.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import hyve.petshow.domain.embeddables.Auditoria;
import hyve.petshow.domain.embeddables.Endereco;
import hyve.petshow.domain.embeddables.Geolocalizacao;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String razaoSocial;
	private String cnpj;
	@Embedded
	private Endereco endereco;
	@Embedded
	private Auditoria auditoria;
	@Embedded
	private Geolocalizacao geolocalizacao;
}
