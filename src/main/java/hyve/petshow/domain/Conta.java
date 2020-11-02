package hyve.petshow.domain;

import hyve.petshow.domain.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.google.common.base.Optional;

@Data
@Entity(name = "conta")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "discriminator",
		discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="G") //Generico
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String nomeSocial;
	private String cpf;
	private String telefone;
	@Enumerated(EnumType.STRING)
	private TipoConta tipo;
	private String foto;
	@Embedded
    private Endereco endereco;
	@Embedded
    private Login login;
	private Boolean enabled;
	
	public String getEmail() {
		return getLogin() == null ? null : getLogin().getEmail();
	}
}
