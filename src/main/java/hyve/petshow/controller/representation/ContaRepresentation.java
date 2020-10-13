package hyve.petshow.controller.representation;

import hyve.petshow.domain.Endereco;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaRepresentation {
	private Long id;
	private String nome;
	private String nomeSocial;
	private String cpf;
	private String telefone;
	private Integer tipo;
	private String foto;
	private Endereco endereco;
	private Login login;

	public void setLoginEmail(Login login){
		this.login = new Login(login.getEmail());
	}
}
