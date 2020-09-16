package hyve.petshow.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import hyve.petshow.domain.enums.TipoConta;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue(value="C") //Cliente
public class Cliente extends Conta {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_conta")
	private List<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();

	public Cliente() {
	}

	public Cliente(Long id, String nome, String nomeSocial, String cpf, String telefone, TipoConta tipo, String foto,
			Endereco endereco, Login login, List<AnimalEstimacao> animaisEstimacao) {
		super(id, nome, nomeSocial, cpf, telefone, tipo, foto, endereco, login);
		setAnimaisEstimacao(animaisEstimacao);
	}

}
