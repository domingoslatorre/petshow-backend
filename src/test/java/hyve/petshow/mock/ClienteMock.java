package hyve.petshow.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Endereco;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;

import static hyve.petshow.mock.AnimalEstimacaoMock.animalEstimacao;
import static hyve.petshow.mock.ContaMock.conta;

public class ClienteMock {
	public static List<Cliente> dbMock = new ArrayList<Cliente>(Arrays.asList(new Cliente(conta(), Arrays.asList(animalEstimacao()))));
	
	public static Cliente criaCliente() {
		var cliente = new Cliente();
		cliente.setId(1l);
		cliente.setNome("Teste");
		cliente.setCpf("44444444444"); 
		cliente.setTelefone("1129292828");
		cliente.setTipo(TipoConta.CLIENTE);
		cliente.setEndereco(new Endereco());
		var login = new Login();
		login.setEmail("teste-login@teste.com");
		login.setSenha("teste1234");
		cliente.setLogin(login);
		cliente.setAnimaisEstimacao(new ArrayList<AnimalEstimacao>());
		
		return cliente;
	}
	
	public static List<Cliente> obterContas() {
		return dbMock;
	}

	public static Optional<Cliente> buscaPorId(Long id) {
		return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
	}
	
	public static Optional<Cliente> buscaPorLogin(Login login) {
		return dbMock.stream().filter(el -> el.getLogin().equals(login)).findFirst();
	}
	
	public static Cliente salvaConta(Cliente conta) {
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
	
	public static void removePorId(Long id) {
		dbMock = dbMock.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
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

	public static Optional<Cliente> buscarPorEmail(String email) {
		return dbMock.stream()
				.filter(el -> email.equals(el.getLogin().getEmail()))
				.findFirst();
	}

	public static Optional<Cliente> buscaPorCpf(String cpf) {
		return dbMock.stream()
				.filter(el -> cpf.equals(el.getCpf()))
				.findFirst();
	}
}
