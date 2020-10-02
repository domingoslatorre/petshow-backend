package hyve.petshow.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import hyve.petshow.domain.enums.TipoConta;
import lombok.Data;

@Data
@Entity
//@DiscriminatorValue(value = "C") // Cliente
public class Cliente extends Conta {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_conta")
	private List<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
	@OneToMany
	@JoinColumn(name = "fk_conta")
	private List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

	public Cliente() {
	}

	public Cliente(Long id, String nome, String nomeSocial, String cpf, String telefone, TipoConta tipo, String foto,
			Endereco endereco, Login login, List<AnimalEstimacao> animaisEstimacao) {
		super(id, nome, nomeSocial, cpf, telefone, tipo, foto, endereco, login);
		setAnimaisEstimacao(animaisEstimacao);
	}

}
