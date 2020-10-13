package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.ServicoConverter;
import hyve.petshow.mock.ServicoMock;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")

public class ServicoConverterTest {
	private ServicoConverter converter = new ServicoConverter();
	
	@Test
	public void deve_retornar_servico_convertido() {
		//dado
		var actual = ServicoMock.criarServico();
		//quando
		var representation = ServicoMock.criarServicoRepresentation();
		//entao
		var expected = converter.toDomain(representation);
		
		assertEquals(actual, expected);
	}

	@Test
	public void deve_retornar_representacao_convertida() {
		
		var actual = ServicoMock.criarServicoRepresentation();
		
		var servico = ServicoMock.criarServico();
		var expected = converter.toRepresentation(servico);
		
		assertEquals(actual, expected);
	}



    @Test
    public void deve_converter_para_lista_de_representation(){
        //dado

        var expected = Arrays.asList(ServicoMock.criarServicoRepresentation());
        var servicos = Arrays.asList(ServicoMock.criarServico());

        //quando
        var actual = converter.toRepresentationList(servicos);

        //entao
        assertEquals(expected, actual);
    }
    
    @Test
    public void deve_converter_para_lista_de_domains(){
        //dado
       	
        var expected = Arrays.asList(ServicoMock.criarServico());
        var representations = Arrays.asList(ServicoMock.criarServicoRepresentation());

        //quando
        var actual = converter.toDomainList(representations);

        //entao
        assertEquals(expected, actual);
    }

}
