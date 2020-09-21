package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.port.ServicoDetalhadoService;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class ServicoDetalhadoServiceTest {

	@Autowired
    private ServicoDetalhadoService service;

    @MockBean
    private ServicoDetalhadoRepository repository;
    
   
    
    @Test
    public void deve_retornar_servicos_detalhados_com_sucesso() {
        //dado
    	var expected = ServicoDetalhadoMock.servicoDetalhado();
    	List<ServicoDetalhado> listaExpected = new ArrayList<>();
        listaExpected.add(expected);  
        
        var servicoDetalhadoMock = ServicoDetalhadoMock.servicoDetalhado();
        List<ServicoDetalhado> listaMock = new ArrayList<>();
        listaMock.add(servicoDetalhadoMock);  
        
        when(repository.saveAll(listaMock)).thenReturn(listaExpected);

        //quando
        var actual = service.adicionarServicosDetalhados(listaMock);

        //entao
        assertEquals(expected, actual);
    }

    
    @Test
    public void deve_retornar_servico_detalhado_atualizado(){
        //dado
        var servicoDetalhado = Optional.of(ServicoDetalhadoMock.servicoDetalhado());
        var requestBody = ServicoDetalhadoMock.servicoDetalhadoAlt();
        var expected = Optional.of(requestBody);
        var id = 1L;

        when(repository.findById(id)).thenReturn(servicoDetalhado);
        when(repository.save(requestBody)).thenReturn(requestBody);

        //quando
        var actual = service.atualizarServicoDetalhado(id, requestBody);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_vazio_se_animal_nao_existir(){
        //dado
        Optional<ServicoDetalhado> servicoDetalhado = Optional.empty();
        var requestBody = ServicoDetalhadoMock.servicoDetalhadoAlt();
        var expected = Optional.empty();
        var id = 1L;

        when(repository.findById(id)).thenReturn(servicoDetalhado);

        //quando
        var actual = service.atualizarServicoDetalhado(id, requestBody);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_remover_animal_de_estimacao() throws Exception{
        //dado
        var id = 1L;
        var expected = ServicoDetalhadoMock.mensagem();

        when(repository.existsById(id)).thenReturn(Boolean.FALSE);

        //quando
        var actual = service.removerServicoDetalhado(id);

        //entao
        assertEquals(expected, actual);
    }

}
