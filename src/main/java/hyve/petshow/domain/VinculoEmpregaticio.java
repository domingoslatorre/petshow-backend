package hyve.petshow.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hyve.petshow.domain.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VinculoEmpregaticio implements Serializable {
	private static final long serialVersionUID = -8403753014189610381L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_prestador", referencedColumnName = "id")
	@ToString.Exclude
	private Prestador prestador;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_empresa", referencedColumnName = "id", nullable = false)
	@ToString.Exclude
	private Empresa empresa;

	private Cargo cargo;

}
