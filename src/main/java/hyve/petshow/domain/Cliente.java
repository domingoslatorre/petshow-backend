package hyve.petshow.domain;

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

	public Cliente(Conta conta) {
		super(conta.getId(), conta.getNome(), conta.getNomeSocial(), conta.getCpf(), conta.getTelefone(),
				conta.getTipo(), conta.getFoto(), conta.getEndereco(), conta.getLogin(), false);
	}

	public Cliente(Conta conta, List<AnimalEstimacao> animaisEstimacao){
		this(conta);
		setAnimaisEstimacao(animaisEstimacao);
	}
}
