package hyve.petshow.controller.representation;

import hyve.petshow.domain.embeddables.Endereco;
import hyve.petshow.domain.embeddables.Geolocalizacao;
import hyve.petshow.domain.embeddables.Login;
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
	private Float mediaAvaliacao;
	private Endereco endereco;
	private Login login;
	private Geolocalizacao geolocalizacao;
}
