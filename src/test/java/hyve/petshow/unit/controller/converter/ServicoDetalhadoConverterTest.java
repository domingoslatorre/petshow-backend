package hyve.petshow.unit.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.mock.ServicoDetalhadoMock;


@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class ServicoDetalhadoConverterTest {
	@Autowired
	private ServicoDetalhadoConverter converter;
	
	@Test //esse teste converte pra representa��o e pra dominio de novo, ent�o se ele est� correto os dois processos est�o corretos
	public void deve_retornar_servicoDetalhado_convertido() {
		var expected = ServicoDetalhadoMock.criarServicoDetalhado();
		expected.setPrestadorId(null);
		var representation = ServicoDetalhadoMock.criarServicoDetalhadoRepresentation();
		representation.setPrestadorId(null);
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
    	representation.setPrestadorId(null);
        var expectedValue = ServicoDetalhadoMock.criarServicoDetalhado();
        expectedValue.setPrestadorId(null);
		var expected = Arrays.asList(expectedValue);
        var representations = Arrays.asList(representation);

        //quando
        var actual = converter.toDomainList(representations);

        //entao
        assertEquals(expected, actual);
    }

}
