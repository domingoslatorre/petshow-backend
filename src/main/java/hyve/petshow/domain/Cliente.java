package hyve.petshow.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import hyve.petshow.domain.enums.TipoConta;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Cliente extends Conta {
	@OneToMany(mappedBy = "dono", cascade = CascadeType.ALL)
	List<AnimalEstimacao> animaisEstimacao;

	public Cliente() {
	}
	
	public Cliente(Long id, String nome, String nomeSocial, String cpf, 
				  String telefone, TipoConta tipo, String foto, Endereco endereco, Login login, List<AnimalEstimacao> animaisEstimacao) {
		super(id, nome, nomeSocial, cpf, telefone, tipo, foto, endereco, login);
		setAnimaisEstimacao(animaisEstimacao);
	}
	

}
