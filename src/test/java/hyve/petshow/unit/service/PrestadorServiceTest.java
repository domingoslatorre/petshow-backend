package hyve.petshow.unit.service;



import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.*;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.repository.PrestadorRepository;

import hyve.petshow.service.implementation.PrestadorServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PrestadorServiceTest {
    @Mock // cria um mock do repositorio
    private PrestadorRepository repository;
    @InjectMocks // serviço depende do repositorio, injeta os mocks dentro do serviço
    private PrestadorServiceImpl service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this); // Inicializador

        Mockito.when(repository.findAll())
                .thenReturn(PrestadorMock.obterContas());

        Mockito.when(repository.findById(Mockito.anyLong()))
                .then(mock -> PrestadorMock.buscaPorId(mock.getArgument(0)));

        Mockito.when(repository.findByLogin(Mockito.any(Login.class)))
                .then(mock -> PrestadorMock.buscaPorLogin(mock.getArgument(0)));

        Mockito.when(repository.findByEmail(Mockito.anyString()))
                .then(mock -> PrestadorMock.buscarPorEmail(mock.getArgument(0)));

        Mockito.when(repository.findByCpf(Mockito.anyString()))
                .then(mock -> PrestadorMock.buscaPorCpf(mock.getArgument(0)));

        Mockito.when(repository.save(Mockito.any(Prestador.class)))
                .then(mock -> {
                    Prestador conta = mock.getArgument(0);
                    if (conta.getId() == null) {
                        PrestadorMock.salvaPrestador(conta);
                    } else {
                        PrestadorMock.atualizaConta(conta);
                    }
                    return conta;
                });

        Mockito.when(repository.save(Mockito.any(Prestador.class)))
                .then(mock -> {
                    Prestador conta = mock.getArgument(0);
                    if (conta.getId() == null) {
                        PrestadorMock.salvaConta(conta);
                    } else {
                        PrestadorMock.atualizaConta(conta);
                    }
                    return conta;
                });

        Mockito.doAnswer(mock -> {
            PrestadorMock.removeConta(mock.getArgument(0));
            return null;
        }).when(repository).delete(Mockito.any(Prestador.class));

        Mockito.doAnswer(mock -> {
            PrestadorMock.removePorId(mock.getArgument(0));
            return null;
        }).when(repository).deleteById(Mockito.anyLong());
    }

    @Test
    @Order(1)
    public void deve_atualizar_conta() throws Exception {
        Prestador prestadorAlterar = service.buscarPorId(1l);
        Login login = new Login();
        login.setEmail("teste@teste.com");
        login.setSenha("aslkjdgklsdjg");
        prestadorAlterar.setLogin(login);
        Conta contaDb = service.atualizarConta(1l, prestadorAlterar);
        assertEquals(login, prestadorAlterar.getLogin());
        assertEquals(contaDb.getId(), prestadorAlterar.getId());
    }

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
        assertTrue(!service.buscarContas().isEmpty());
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
}
