package hyve.petshow.domain;

import hyve.petshow.domain.enums.TipoConta;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Data // gera getters and setters e Hashcode
@Entity // nao temos uma entidade do tipo Prestador no MER
 @DiscriminatorValue(value="P") //
public class Prestador extends Conta {
	private String descricao;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_prestador")
	private List<ServicoDetalhado> servicosPrestados = new ArrayList<ServicoDetalhado>();

	public Prestador() {
	}

	public Prestador(Long id, String nome, String nomeSocial, String cpf, String telefone, TipoConta tipo, String foto,
			Endereco endereco, Login login, String descricao
//                       List<ServicoDetalhado> servicosDetalhados
	) {
		super(id, nome, nomeSocial, cpf, telefone, tipo, foto, endereco, login);
		setDescricao(descricao);
//          setServicoDetalhado(servicosDetalhados);
	}

}
