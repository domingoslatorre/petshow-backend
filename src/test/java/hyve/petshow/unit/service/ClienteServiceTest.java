package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.mock.ClienteMock;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.implementation.ClienteServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClienteServiceTest {
	@Mock
	private ClienteRepository repository;
	@InjectMocks
	private ClienteServiceImpl service;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(repository.findAll()).thenReturn(ServicoDetalhadoMock.obterContas());
		Mockito.when(repository.save(Mockito.any(ServicoDetalhado.class))).then(mock -> {
			ServicoDetalhado conta = mock.getArgument(0);
			if (conta.getId() == null) {
				ServicoDetalhadoMock.salvaConta(conta);
			} else {
				ServicoDetalhadoMock.atualizaConta(conta);
			}
			return conta;
		});
		Mockito.doAnswer(mock -> {
			ServicoDetalhadoMock.removeConta(mock.getArgument(0));
			return null;
		}).when(repository).delete(Mockito.any(ServicoDetalhado.class));
		Mockito.when(repository.findById(Mockito.anyLong())).then(mock -> ServicoDetalhadoMock.buscaPorId(mock.getArgument(0)));
		Mockito.when(repository.findByLogin(Mockito.any(Login.class)))
				.then(mock -> ServicoDetalhadoMock.buscaPorLogin(mock.getArgument(0)));

		Mockito.when(repository.findByEmail(Mockito.anyString()))
				.then(mock -> ServicoDetalhadoMock.buscarPorEmail(mock.getArgument(0)));
		Mockito.when(repository.findByCpf(Mockito.anyString()))
				.then(mock -> ServicoDetalhadoMock.buscaPorCpf(mock.getArgument(0)));
		Mockito.doAnswer(mock -> {
			ServicoDetalhadoMock.removePorId(mock.getArgument(0));
			return null;
		}).when(repository).deleteById(Mockito.anyLong());
	}

	@Test
	@Order(1)
	public void deve_inserir_cliente_na_lista() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setNome("Danilo");
		Login login = new Login();
		login.setEmail("");
		cliente.setCpf("555555555555");
		cliente.setLogin(login);
		service.salvaConta(cliente);
		assertTrue(repository.findAll().contains(cliente));
		assertNotNull(cliente.getId());
	}

	@Test
	@Order(2)
	public void deve_atualizar_conta() throws Exception {
		Cliente clienteAAlterar = service.obterContaPorId(1l);
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("aslkjdgklsdjg");
		clienteAAlterar.setLogin(login);
		Conta contaDb = service.atualizaConta(clienteAAlterar);
		assertEquals(login, clienteAAlterar.getLogin());
		assertEquals(contaDb.getId(), clienteAAlterar.getId());
	}

	@Test
	@Order(3)
	public void deve_atualizar_animal() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setId(2l);
		ArrayList<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
		AnimalEstimacao animalTeste = new AnimalEstimacao();
		animalTeste.setNome("Joao");
		animaisEstimacao.add(animalTeste);
		cliente.setAnimaisEstimacao(animaisEstimacao);

		Cliente salvaConta = (Cliente) service.atualizaConta(cliente);
		assertTrue(salvaConta.getAnimaisEstimacao().contains(animalTeste));

	}

	@Test
	@Order(4)
	public void deve_encontrar_conta_correta() throws Exception {
		Conta obterContaPorId = service.obterContaPorId(1l);
		assertEquals("Teste", obterContaPorId.getNome());
	}

	@Test
	@Order(5)
	public void deve_retornar_excecao_por_pessoa_nao_encontrada() {
		assertThrows(Exception.class, () -> {
			service.obterContaPorId(5l);
		});
	}

	@Test
	@Order(6)
	public void deve_encontrar_elemento_por_login() throws Exception {
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("aslkjdgklsdjg");
		Conta obterPorLogin = service.obterPorLogin(login);
		assertNotNull(obterPorLogin);
		assertTrue(obterPorLogin.getId() == 1);
	}

	@Test
	@Order(7)
	public void deve_encontrar_todos_os_elementos() {
		assertTrue(!service.obterContas().isEmpty());
	}

	@Test
	@Order(8)
	public void deve_remover_elemento() throws Exception {
		service.removerConta(1l);
		assertThrows(Exception.class, () -> {
			service.obterContaPorId(1l);
		});
	}

	@Test
	@Order(9)
	public void deve_retornar_mensagem_sucesso() throws Exception {
		MensagemRepresentation removerConta = service.removerConta(2l);
		assertEquals(MensagemRepresentation.MENSAGEM_SUCESSO, removerConta.getMensagem());
		assertTrue(removerConta.getSucesso());
	}

	@Test
	@Order(11)
	public void deve_impedir_insercao_contas_com_mesmo_email() throws Exception {
		Cliente conta = new Cliente();
		Login login = new Login();
		login.setEmail("teste@teste");
		conta.setLogin(login);
		conta.setCpf("22222222222");
		service.salvaConta(conta);
		assertThrows(Exception.class, () -> {
			service.salvaConta(conta);
		});
	}

	@Test
	@Order(12)
	public void deve_impedir_insercao_contas_com_mesmo_cpf() throws Exception {
		Cliente conta = new Cliente();
		conta.setCpf("44444444444");
		Login login = new Login();
		login.setEmail("asdgs@aslkdjg");
		login.setSenha("03joiwk");
		conta.setLogin(login);
		service.salvaConta(conta);
		assertThrows(Exception.class, () -> {
			service.salvaConta(conta);
		});

	}
}
