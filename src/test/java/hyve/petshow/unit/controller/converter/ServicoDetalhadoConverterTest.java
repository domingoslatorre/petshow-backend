package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.ServicoConverter;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.ServicoDetalhadoMock;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")

public class ServicoDetalhadoConverterTest {
	private ServicoDetalhadoConverter converter = new ServicoDetalhadoConverter();
	
	@Test //esse teste converte pra representação e pra dominio de novo, então se ele está correto os dois processos estão corretos
	public void deve_retornar_servicoDetalhado_convertido() {
		var expected = ServicoDetalhadoMock.criarServicoDetalhado();
		
		var representation = ServicoDetalhadoMock.criarServicoDetalhadoRepresentation();
		
		var actual = converter.toDomain(representation);
		assertEquals(actual, expected);
	}

//	@Test
//	public void deve_retornar_representacao_convertida() {
//		ServicoDetalhadoRepresentation representation = new ServicoDetalhadoRepresentation();
//		representation.setId(1L);
//		BigDecimal p = new BigDecimal(70);
//		representation.setPreco(p);
//		Servico s = new Servico();
//    	s.setId(Long.valueOf(1));
//    	s.setNome("Banho e Tosa");
//    	s.setDescricao("Banhos quentinhos para o seu pet");
//    	ServicoConverter servicoConverter = new ServicoConverter();
//    	representation.setTipo(servicoConverter.toRepresentation(s));
//    	
//		ServicoDetalhado domain = converter.toDomain(representation);
//		
//		assertEquals(representation.getId(), domain.getId());
//	}



    @Test
    public void deve_converter_para_lista_de_representation(){
        //dado
    	var representation = ServicoDetalhadoMock.criarServicoDetalhadoRepresentation();
    	
        var expected = Arrays.asList(representation);
        var servicosDetalhados = Arrays.asList(ServicoDetalhadoMock.criarServicoDetalhado());

        //quando
        var actual = converter.toRepresentationList(servicosDetalhados);

        //entao
        assertEquals(expected, actual);
    }
    
    @Test
    public void deve_converter_para_lista_de_domains(){
        //dado
    	var representation = ServicoDetalhadoMock.criarServicoDetalhadoRepresentation();
    	
        var expected = Arrays.asList(ServicoDetalhadoMock.criarServicoDetalhado());
        var representations = Arrays.asList(representation);

        //quando
        var actual = converter.toDomainList(representations);

        //entao
        assertEquals(expected, actual);
    }

}
