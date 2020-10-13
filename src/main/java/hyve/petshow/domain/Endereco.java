package hyve.petshow.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Endereco {
	private String logradouro;
	private String cep;
	private String bairro;
	private String cidade;
	private String estado;
	private String numero;
	private String complemento;
}
