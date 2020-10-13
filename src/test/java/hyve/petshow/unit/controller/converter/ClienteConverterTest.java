package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static hyve.petshow.mock.AnimalEstimacaoMock.animalEstimacaoList;
import static hyve.petshow.mock.AnimalEstimacaoMock.tipoAnimalEstimacaoRepresentation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClienteConverterTest {
	@Mock
	private AnimalEstimacaoConverter animalConverter;

	@InjectMocks
	private ClienteConverter converter = new ClienteConverter();

	@BeforeEach
	public void init(){
		initMocks(this);
	}

	@Test
	public void deve_retornar_cliente_convertido() {
		Cliente cliente = new Cliente();
		cliente.setId(1l);
		ArrayList<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
		AnimalEstimacao animal = new AnimalEstimacao();
		animaisEstimacao.add(animal);
		cliente.setAnimaisEstimacao(animaisEstimacao);

		doReturn(animalEstimacaoList()).when(animalConverter).toRepresentationList(cliente.getAnimaisEstimacao());

		ClienteRepresentation representation = converter.toRepresentation(cliente);
		
		assertEquals(cliente.getId(), representation.getId());
		assertFalse(representation.getAnimaisEstimacao().isEmpty());
	}
	
	@Test
	public void deve_retornar_representacao_convertida() {
		ClienteRepresentation representation = new ClienteRepresentation();
		representation.setId(1l);
		List<AnimalEstimacaoRepresentation> animais = new ArrayList<AnimalEstimacaoRepresentation>();
		AnimalEstimacaoRepresentation animal = new AnimalEstimacaoRepresentation();
		animal.setTipo(tipoAnimalEstimacaoRepresentation());

		animais.add(animal);
		
		representation.setAnimaisEstimacao(animais);
		
		Cliente domain = converter.toDomain(representation);
		
		assertEquals(representation.getId(), domain.getId());
	}

}
