package hyve.petshow.unit.service;

import java.util.Optional;

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
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.mock.ClienteMock;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.implementation.ClienteServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		Mockito.when(repository.findAll()).thenReturn(ClienteMock.obterContas());
		Mockito.when(repository.save(Mockito.any(Cliente.class))).then(mock -> {
			Cliente conta = mock.getArgument(0);
			if (conta.getId() == null) {
				ClienteMock.salvaConta(conta);
			} else {
				ClienteMock.atualizaConta(conta);
			}
			return conta;
		});
		Mockito.doAnswer(mock -> {
			ClienteMock.removeConta(mock.getArgument(0));
			return null;
		}).when(repository).delete(Mockito.any(Cliente.class));
		Mockito.when(repository.findById(Mockito.anyLong())).then(mock -> ClienteMock.buscaPorId(mock.getArgument(0)));
		Mockito.when(repository.findByEmail(Mockito.anyString()))
				.then(mock -> ClienteMock.buscarPorEmail(mock.getArgument(0)));
		Mockito.when(repository.findByCpf(Mockito.anyString()))
				.then(mock -> ClienteMock.buscaPorCpf(mock.getArgument(0)));
		Mockito.doAnswer(mock -> {
			ClienteMock.removePorId(mock.getArgument(0));
			return null;
		}).when(repository).deleteById(Mockito.anyLong());
	}

	@Test
	@Order(1)
	public void deve_atualizar_conta() throws Exception {
		Cliente clienteAAlterar = service.buscarPorId(1l);
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("aslkjdgklsdjg");
		clienteAAlterar.setLogin(login);
		Conta contaDb = service.atualizarConta(1l, clienteAAlterar);
		assertEquals(login, clienteAAlterar.getLogin());
		assertEquals(contaDb.getId(), clienteAAlterar.getId());
	}

	/*
	 * @Test
	 * 
	 * @Order(2) public void deve_atualizar_animal() throws Exception { Cliente
	 * cliente = new Cliente(); cliente.setId(2l); ArrayList<AnimalEstimacao>
	 * animaisEstimacao = new ArrayList<AnimalEstimacao>(); AnimalEstimacao
	 * animalTeste = new AnimalEstimacao(); animalTeste.setNome("Joao");
	 * animaisEstimacao.add(animalTeste);
	 * cliente.setAnimaisEstimacao(animaisEstimacao);
	 * 
	 * Cliente salvaConta = (Cliente) service.atualizarConta(1l, cliente);
	 * assertTrue(salvaConta.getAnimaisEstimacao().contains(animalTeste));
	 * 
	 * }
	 */

	@Test
	@Order(2)
	public void deve_encontrar_conta_correta() throws Exception {
		Conta obterContaPorId = service.buscarPorId(1l);
		assertEquals("Teste", obterContaPorId.getNome());
	}

	@Test
	@Order(3)
	public void deve_retornar_excecao_por_pessoa_nao_encontrada() {
		assertThrows(Exception.class, () -> {
			service.buscarPorId(5l);
		});
	}

	@Test
	@Order(4)
	public void deve_encontrar_todos_os_elementos() {
		assertFalse(service.buscarContas().isEmpty());
	}

	@Test
	@Order(5)
	public void deve_remover_elemento() throws Exception {
		service.removerConta(1l);
		assertThrows(Exception.class, () -> {
			service.buscarPorId(1l);
		});
	}

	@Test
	@Order(6)
	public void deve_retornar_mensagem_sucesso() throws Exception {
		MensagemRepresentation removerConta = service.removerConta(2l);
		assertEquals(MensagemRepresentation.MENSAGEM_SUCESSO, removerConta.getMensagem());
		assertTrue(removerConta.getSucesso());
	}

	@Test
	@Order(7)
	public void deve_retornar_mensagem_de_erro() throws Exception {
		Mockito.when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		var removerConta = service.removerConta(22l);
		assertEquals(MensagemRepresentation.MENSAGEM_FALHA, removerConta.getMensagem());
	}

	@Test
	@Order(8)
	public void deve_encontrar_por_email() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new Cliente()));

		var buscarPorEmail = service.buscarPorEmail("teste@teste");
		assertNotNull(buscarPorEmail);
	}
}
