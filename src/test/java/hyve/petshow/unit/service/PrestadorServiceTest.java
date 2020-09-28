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

import java.util.ArrayList;

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
    public void deve_inserir_prestador_na_lista() throws Exception {
        Prestador prestador = new Prestador();
        prestador.setNome("Prestador_1");
        Login login = new Login();
        login.setEmail("teste@teste.com");
        prestador.setCpf("555555555555");
        prestador.setLogin(login);
        service.salvaConta(prestador);
        assertTrue(repository.findAll().contains(prestador));
        assertNotNull(prestador.getId());
    }

    @Test
    @Order(2)
    public void deve_atualizar_conta() throws Exception {
        Prestador prestadorAlterar = service.obterContaPorId(1l);
        Login login = new Login();
        login.setEmail("teste@teste.com");
        login.setSenha("aslkjdgklsdjg");
        prestadorAlterar.setLogin(login);
        Conta contaDb = service.atualizaConta(prestadorAlterar);
        assertEquals(login, prestadorAlterar.getLogin());
        assertEquals(contaDb.getId(), prestadorAlterar.getId());
    }

//    @Test
//    @Order(3)
//    public void deve_atualizar_servicoDetalhado() throws Exception {
//        Prestador prestador = new Prestador();
//        prestador.setId(2l);
//        ArrayList<ServicoDetalhado> servicosDetalhados = new ArrayList<ServicoDetalhado>();
//        ServicoDetalhado servicoDetalhadoTeste = new ServicoDetalhado();
//        servicoDetalhadoTeste.setNome("Joao");
//        servicosDetalhados.add(servicoDetalhadoTeste);
//        prestador.setServicoDetalhado(servicosDetalhados);
//
//        Prestador salvaConta = (Prestador) service.atualizaConta(prestador);
//        assertTrue(salvaConta.getServicoDetalhado().contains(servicoDetalhadoTeste));
//    }

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
        Prestador conta = new Prestador();
        Login login = new Login();
        login.setEmail("teste@teste.com");
        conta.setLogin(login);
        conta.setCpf("22222222222");
        service.salvaConta(conta);
        conta.setCpf("286238623846");
        assertThrows(Exception.class, () -> {
            service.salvaConta(conta);
        });
    }

    @Test
    @Order(12)
    public void deve_impedir_insercao_contas_com_mesmo_cpf() throws Exception {
        Prestador conta = new Prestador();
        conta.setCpf("44444444444");
        Login login = new Login();
        login.setEmail("asdgs@aslkdjg");
        login.setSenha("03joiwk");
        conta.setLogin(login);
        service.salvaConta(conta);
        Login login2 = new Login();
        login2.setEmail("asdgs@aslkdjg");
        login2.setSenha("03joiwk");
        conta.setLogin(login2);

        assertThrows(Exception.class, () -> {
            service.salvaConta(conta);
        });

    }

}
