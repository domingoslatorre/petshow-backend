package hyve.petshow.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import hyve.petshow.domain.enums.Cargo;
import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "vinculo_empregaticio")
public class VinculoEmpregaticio {
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_prestador")
	@ToString.Exclude
	private Prestador prestador;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_empresa", referencedColumnName = "id", nullable = false)
	@ToString.Exclude
	private Empresa empresa;

	private Cargo cargo;

}
