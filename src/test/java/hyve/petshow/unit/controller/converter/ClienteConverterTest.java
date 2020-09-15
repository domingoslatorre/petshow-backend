package hyve.petshow.unit.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import hyve.petshow.domain.enums.TipoAnimalEstimacao;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClienteConverterTest {
	private ClienteConverter converter = new ClienteConverter();
	
	@Test
	public void deve_retornar_cliente_convertido() {
		Cliente cliente = new Cliente();
		cliente.setId(1l);
		ArrayList<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
		AnimalEstimacao animal = new AnimalEstimacao();
		animaisEstimacao.add(animal);
		cliente.setAnimaisEstimacao(animaisEstimacao);
		
		ClienteRepresentation representation = converter.toRepresentation(cliente);
		
		assertEquals(cliente.getId(), representation.getId());
		assertTrue(!representation.getAnimaisEstimacao().isEmpty());
	}
	
	@Test
	public void deve_retornar_representacao_convertida() {
		ClienteRepresentation representation = new ClienteRepresentation();
		representation.setId(1l);
		List<AnimalEstimacaoRepresentation> animais = new ArrayList<AnimalEstimacaoRepresentation>();
		AnimalEstimacaoRepresentation animal = new AnimalEstimacaoRepresentation();
		animal.setTipo(TipoAnimalEstimacao.GATO);

		animais.add(animal);
		
		representation.setAnimaisEstimacao(animais);
		
		Cliente domain = converter.toDomain(representation);
		
		assertEquals(representation.getId(), domain.getId());
		assertTrue(!domain.getAnimaisEstimacao().isEmpty());
	}

}
