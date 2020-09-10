package hyve.petshow.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Endereco;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;

public class ContaMock {
	public static List<Conta> dbMock = new ArrayList<Conta>(Arrays.asList(new Conta(1l, "Teste", "Teste", "44444444444", "1129292828", TipoConta.CLIENTE, "", new Endereco(), new Login())));
	
	public static List<Conta> obterContas() {
		return dbMock;
	}

	public static Optional<Conta> buscaPorId(Long id) {
		return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
	}
	
	public static Optional<Conta> buscaPorLogin(Login login) {
		return dbMock.stream().filter(el -> el.getLogin().equals(login)).findFirst();
	}
	
	public static Conta salvaConta(Conta conta) {
		conta.setId((long) dbMock.size() + 1);
		dbMock.add(conta);		
		return conta;
	}
	
	
	private static Cliente salvaCliente(Cliente cliente) {
		Cliente buscaPorId = (Cliente) buscaPorId(cliente.getId()).get();
		buscaPorId.setAnimaisEstimacao(cliente.getAnimaisEstimacao());
		return buscaPorId;
	}
	
	public static void removeConta(Conta conta) {
		dbMock.remove(conta);
	}

	public static Conta atualizaConta(Conta conta) {
		Conta contaDb = buscaPorId(conta.getId()).get();
		contaDb.setCpf(conta.getCpf());
		contaDb.setEndereco(conta.getEndereco());
		contaDb.setFoto(conta.getFoto());
		contaDb.setLogin(conta.getLogin());
		contaDb.setNome(conta.getNome());
		contaDb.setNomeSocial(conta.getNomeSocial());
		contaDb.setTelefone(conta.getTelefone());
		contaDb.setTipo(conta.getTipo());
		if(conta instanceof Cliente) {
			salvaCliente((Cliente) conta);
		}
		
		return contaDb;
		
	}
}
