package hyve.petshow.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Endereco;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.enums.TipoConta;

public class PrestadorMock {
    private static final Endereco PRESTADOR_ENDERECO = new Endereco();
	private static final String PRESTADOR_FOTO = "";
	private static final Integer PRESTADOR_TIPO = TipoConta.PRESTADOR_AUTONOMO.getTipo();
	private static final String PRESTADOR_TELEFONE = "1129292828";
	private static final String PRESTADOR_CPF = "44444444444";
	private static final String PRESTADOR_NOME = "Teste";
	private static final Long PRESTADOR_ID = 1l;
	private static final String PRESTADOR_SENHA = "teste1234";
	private static final String PRESTADOR_EMAIL = "teste-login-prestador@teste.com";
	private static final String PRESTADOR_DESCRICAO = "";
	
	private static List<Prestador> dbMock = new ArrayList<Prestador>(Arrays.asList(criaPrestador()));
    // , new ArrayList<servicosDetalhados>()
    
    
    public static Prestador criaPrestador() {
    	var login = new Login();
    	login.setEmail(PRESTADOR_EMAIL);
    	login.setSenha(PRESTADOR_SENHA);
    	
    	var prestador = new Prestador(1l, PRESTADOR_NOME, PRESTADOR_NOME, PRESTADOR_CPF, PRESTADOR_TELEFONE, TipoConta.PRESTADOR_AUTONOMO, PRESTADOR_FOTO, PRESTADOR_ENDERECO, login, PRESTADOR_FOTO);
    	
    	prestador.setServicosPrestados(new ArrayList<ServicoDetalhado>());  
    	
		return prestador;
    }
    public static List<Prestador> obterContas() {
        return dbMock;
    }

    public static Optional<Prestador> buscaPorId(Long id) {
        return dbMock.stream().filter(el -> el.getId().equals(id)).findFirst();
    }

    public static Optional<Prestador> buscaPorLogin(Login login) {
        return dbMock.stream().filter(el -> el.getLogin().equals(login)).findFirst();
    }

    public static Optional<Prestador> buscarPorEmail(String email) {
        return dbMock.stream()
                .filter(el -> email.equals(el.getLogin().getEmail()))
                .findFirst();
    }

    public static Optional<Prestador> buscaPorCpf(String cpf) {
        return dbMock.stream()
                .filter(el -> cpf.equals(el.getCpf()))
                .findFirst();
    }

    public static Prestador salvaPrestador(Prestador prestador) {
        Prestador buscaPorId = (Prestador) buscaPorId(prestador.getId()).get();
//        buscaPorId.setServicoDetalhado(cliente.getServicoDetalhado());
        return buscaPorId;
    }

    public static Prestador salvaConta(Prestador conta) {
        conta.setId((long) dbMock.size() + 1);
        dbMock.add(conta);
        return conta;
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
        if(conta instanceof Prestador) {
            salvaPrestador((Prestador) conta);
        }

        return contaDb;

    }
	public static PrestadorRepresentation criaRepresentation() {
		var representation = new PrestadorRepresentation();
		
		var login = new Login();
    	login.setEmail(PRESTADOR_EMAIL);
    	login.setSenha(PRESTADOR_SENHA);
    	representation.setLogin(login);
    	representation.setId(PRESTADOR_ID);
    	representation.setNome(PRESTADOR_NOME);
    	representation.setNomeSocial(PRESTADOR_NOME);
    	representation.setCpf(PRESTADOR_CPF);
    	representation.setTelefone(PRESTADOR_TELEFONE);
    	representation.setTipo(PRESTADOR_TIPO);
    	representation.setFoto(PRESTADOR_FOTO);
    	representation.setEndereco(PRESTADOR_ENDERECO);
    	representation.setDescricao(PRESTADOR_DESCRICAO);
    	
    	representation.setServicos(new ArrayList<>());
		return representation;
	}

    public static PrestadorRepresentation criaRepresentationSemSenha() {
        var representation = new PrestadorRepresentation();

        var login = new Login();
        login.setEmail(PRESTADOR_EMAIL);
        login.setSenha(null);
        representation.setLogin(login);
        representation.setId(PRESTADOR_ID);
        representation.setNome(PRESTADOR_NOME);
        representation.setNomeSocial(PRESTADOR_NOME);
        representation.setCpf(PRESTADOR_CPF);
        representation.setTelefone(PRESTADOR_TELEFONE);
        representation.setTipo(PRESTADOR_TIPO);
        representation.setFoto(PRESTADOR_FOTO);
        representation.setEndereco(PRESTADOR_ENDERECO);
        representation.setDescricao(PRESTADOR_DESCRICAO);

        representation.setServicos(new ArrayList<>());
        return representation;
    }
}