package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.MensagemRepresentation;

import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.implementation.ServicoDetalhadoServiceImpl;


@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)

public class ServicoDetalhadoServiceTest {

	@Mock
	private ServicoDetalhadoRepository repository;
	@InjectMocks
	private ServicoDetalhadoServiceImpl service;
    
    @BeforeEach
    public void init() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(repository.findAll()).thenReturn(ServicoDetalhadoMock.findAll());
		
		Mockito.when(repository.save(Mockito.any(ServicoDetalhado.class))).then(mock -> {
			ServicoDetalhado servicoDetalhado = mock.getArgument(0);
			if (servicoDetalhado.getId() == null) {
				ServicoDetalhadoMock.adicionarServicoDetalhado(servicoDetalhado);
			} else {
				ServicoDetalhadoMock.atualizarServicoDetalhado(servicoDetalhado);
			}
			return servicoDetalhado;
		});

		
		Mockito.when(repository.findById(Mockito.anyLong())).then(mock -> ServicoDetalhadoMock.findById(mock.getArgument(0)));
		
		Mockito.doAnswer(mock -> {
			ServicoDetalhadoMock.removerPorId(mock.getArgument(0));
			return null;
		}).when(repository).deleteById(Mockito.anyLong());
	}
    
    @Test
	@Order(1)
	public void deve_inserir_servico_detalhado_na_lista() throws Exception {
		ServicoDetalhado servicoDetalhado = new ServicoDetalhado();
		servicoDetalhado.setId(1L);
    	BigDecimal p = new BigDecimal(70);
    	servicoDetalhado.setPreco(p);
    	Servico s = new Servico();
    	s.setId(Long.valueOf(1));
    	s.setNome("Banho e Tosa");
    	s.setDescricao("Banhos quentinhos para o seu pet");
    	servicoDetalhado.setTipo(s);
    	List<ServicoDetalhado> servicosDetalhados = new ArrayList<ServicoDetalhado>();
    	servicosDetalhados.add(servicoDetalhado);
    	
		service.adicionarServicosDetalhados(servicosDetalhados);
		
		assertTrue(repository.findAll().contains(servicoDetalhado));
		
		assertNotNull(servicoDetalhado.getId());
	}
    
    
	@Test
	@Order(2)
	public void deve_atualizar_servico_detalhado() throws Exception {
		Optional<ServicoDetalhado> servicoDetalhadoAAlterar = repository.findById(1L);
		BigDecimal p = new BigDecimal(800);
		servicoDetalhadoAAlterar.get().setPreco(p);
		Optional<ServicoDetalhado> servicoDetalhadoDb = service.atualizarServicoDetalhado(servicoDetalhadoAAlterar.get().getId(),servicoDetalhadoAAlterar.get());
		assertEquals(p, servicoDetalhadoAAlterar.get().getPreco());
		assertEquals(servicoDetalhadoDb.get().getId(), servicoDetalhadoAAlterar.get().getId());
	}
    
	@Test
	@Order(3)
	public void deve_remover_elemento() throws Exception {
		service.removerServicoDetalhado(1l);
		assertThrows(Exception.class, () -> {
			repository.findById(1l);
		});
	}
	

	@Test
	@Order(4)
	public void deve_retornar_mensagem_sucesso() throws Exception {
		MensagemRepresentation removerServicoDetalhado = service.removerServicoDetalhado(2l);
		assertEquals(MensagemRepresentation.MENSAGEM_SUCESSO, removerServicoDetalhado.getMensagem());
		assertTrue(removerServicoDetalhado.getSucesso());
	}
	
    
     //ANTIGO:
    
    
    
    
//    
//    @Test
//    @Order(1)
//    public void deve_retornar_servicos_detalhados_com_sucesso() {
//        //dado
//    	var expected = ServicoDetalhadoMock.servicoDetalhado();
//    	List<ServicoDetalhado> listaExpected = new ArrayList<>();
//        listaExpected.add(expected);  
//        
//        var servicoDetalhadoMock = ServicoDetalhadoMock.servicoDetalhado();
//        List<ServicoDetalhado> listaMock = new ArrayList<>();
//        listaMock.add(servicoDetalhadoMock);  
//        
//        when(repository.saveAll(listaMock)).thenReturn(listaExpected);
//
//        //quando
//        var actual = service.adicionarServicosDetalhados(listaMock);
//
//        //entao
//        assertEquals(expected, actual);
//    }
//
//    
//    @Test
//    public void deve_retornar_servico_detalhado_atualizado(){
//        //dado
//        var servicoDetalhado = Optional.of(ServicoDetalhadoMock.servicoDetalhado());
//        var requestBody = ServicoDetalhadoMock.servicoDetalhadoAlt();
//        var expected = Optional.of(requestBody);
//        var id = 1L;
//
//        when(repository.findById(id)).thenReturn(servicoDetalhado);
//        when(repository.save(requestBody)).thenReturn(requestBody);
//
//        //quando
//        var actual = service.atualizarServicoDetalhado(id, requestBody);
//
//        //entao
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void deve_retornar_vazio_se_animal_nao_existir(){
//        //dado
//        Optional<ServicoDetalhado> servicoDetalhado = Optional.empty();
//        var requestBody = ServicoDetalhadoMock.servicoDetalhadoAlt();
//        var expected = Optional.empty();
//        var id = 1L;
//
//        when(repository.findById(id)).thenReturn(servicoDetalhado);
//
//        //quando
//        var actual = service.atualizarServicoDetalhado(id, requestBody);
//
//        //entao
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void deve_remover_animal_de_estimacao() throws Exception{
//        //dado
//        var id = 1L;
//        var expected = ServicoDetalhadoMock.mensagem();
//
//        when(repository.existsById(id)).thenReturn(Boolean.FALSE);
//
//        //quando
//        var actual = service.removerServicoDetalhado(id);
//
//        //entao
//        assertEquals(expected, actual);
//    }

}
