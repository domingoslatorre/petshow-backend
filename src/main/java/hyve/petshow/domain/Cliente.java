package hyve.petshow.domain;

import hyve.petshow.domain.enums.TipoConta;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "C") // Cliente
public class Cliente extends Conta {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_conta")
	private List<AnimalEstimacao> animaisEstimacao;

	public Cliente(Long id, String nome, String nomeSocial, String cpf, String telefone, TipoConta tipo, String foto,
			Endereco endereco, Login login, List<AnimalEstimacao> animaisEstimacao) {
		super(id, nome, nomeSocial, cpf, telefone, tipo, foto, endereco, login);
		setAnimaisEstimacao(animaisEstimacao);
	}

}
