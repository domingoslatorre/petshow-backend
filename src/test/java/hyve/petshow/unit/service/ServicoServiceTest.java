package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import hyve.petshow.mock.ServicoMock;
import hyve.petshow.repository.ServicoRepository;
import hyve.petshow.service.implementation.ServicoServiceImpl;


@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)

public class ServicoServiceTest {

	@Mock
	private ServicoRepository repository;
	@InjectMocks
	private ServicoServiceImpl service;
    
    @BeforeEach
    public void init() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(repository.findAll()).thenReturn(ServicoMock.findAll());
		
		Mockito.when(repository.save(Mockito.any(Servico.class))).then(mock -> {
			Servico servico = mock.getArgument(0);
			if (servico.getId() == null) {
				ServicoMock.adicionarServico(servico);
			} else {
				ServicoMock.atualizarServico(servico);
			}
			return servico;
		});

		
		Mockito.when(repository.findById(Mockito.anyLong())).then(mock -> ServicoMock.findById(mock.getArgument(0)));
		
		Mockito.doAnswer(mock -> {
			ServicoMock.removerPorId(mock.getArgument(0));
			return null;
		}).when(repository).deleteById(Mockito.anyLong());
	}
    
    @Test
	@Order(1)
	public void deve_inserir_servico__na_lista() throws Exception {
		var servico = ServicoMock.criarServico();
    
		service.adicionarServico(servico);
		
		assertTrue(repository.findAll().contains(servico));
		
		assertNotNull(servico.getId());
	}
    
    
	@Test
	@Order(2)
	public void deve_atualizar_servico_() throws Exception {
		Optional<Servico> servicoAAlterar = repository.findById(1L);
		servicoAAlterar.get().setNome("Outro nome qualquer");
		Optional<Servico> servicoDb = service.atualizarServico(servicoAAlterar.get().getId(),servicoAAlterar.get());

		assertEquals(servicoDb.get().getId(), servicoAAlterar.get().getId());
		assertEquals(servicoDb.get().getNome(), servicoAAlterar.get().getNome());
	}
    
	@Test
	@Order(3)
	public void deve_remover_elemento() throws Exception {
		service.removerServico(1L);
		assertThrows(Exception.class, () -> {
			repository.existsById(1L);
		});
	}
	
	

	@Test
	@Order(4)
	public void deve_retornar_mensagem_sucesso() throws Exception {
		MensagemRepresentation removerServico = service.removerServico(2l);
		assertEquals(MensagemRepresentation.MENSAGEM_SUCESSO, removerServico.getMensagem());
		assertTrue(removerServico.getSucesso());
	}
	
    
     //ANTIGO:
    
    
    
    
//    
//    @Test
//    @Order(1)
//    public void deve_retornar_servicos_s_com_sucesso() {
//        //dado
//    	var expected = ServicoMock.servico();
//    	List<Servico> listaExpected = new ArrayList<>();
//        listaExpected.add(expected);  
//        
//        var servicoMock = ServicoMock.servico();
//        List<Servico> listaMock = new ArrayList<>();
//        listaMock.add(servicoMock);  
//        
//        when(repository.saveAll(listaMock)).thenReturn(listaExpected);
//
//        //quando
//        var actual = service.adicionarServicoss(listaMock);
//
//        //entao
//        assertEquals(expected, actual);
//    }
//
//    
//    @Test
//    public void deve_retornar_servico__atualizado(){
//        //dado
//        var servico = Optional.of(ServicoMock.servico());
//        var requestBody = ServicoMock.servicoAlt();
//        var expected = Optional.of(requestBody);
//        var id = 1L;
//
//        when(repository.findById(id)).thenReturn(servico);
//        when(repository.save(requestBody)).thenReturn(requestBody);
//
//        //quando
//        var actual = service.atualizarServico(id, requestBody);
//
//        //entao
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void deve_retornar_vazio_se_animal_nao_existir(){
//        //dado
//        Optional<Servico> servico = Optional.empty();
//        var requestBody = ServicoMock.servicoAlt();
//        var expected = Optional.empty();
//        var id = 1L;
//
//        when(repository.findById(id)).thenReturn(servico);
//
//        //quando
//        var actual = service.atualizarServico(id, requestBody);
//
//        //entao
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void deve_remover_animal_de_estimacao() throws Exception{
//        //dado
//        var id = 1L;
//        var expected = ServicoMock.mensagem();
//
//        when(repository.existsById(id)).thenReturn(Boolean.FALSE);
//
//        //quando
//        var actual = service.removerServico(id);
//
//        //entao
//        assertEquals(expected, actual);
//    }

}
