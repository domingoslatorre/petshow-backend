package hyve.petshow.controller.representation;


import hyve.petshow.domain.embeddables.Endereco;
import hyve.petshow.domain.embeddables.Geolocalizacao;
import lombok.Data;

@Data
public class EmpresaRepresentation {
	private Long id;
	private String nome;
	private String razaoSocial;
	private String cnpj;
	private Endereco endereco;
	private Geolocalizacao geolocalizacao;
}
