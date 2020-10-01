package hyve.petshow.unit.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.mock.entidades.AvaliacaoMock;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AvaliacaoConverterTest {
	private AvaliacaoConverter converter = new AvaliacaoConverter();

	@Test
	public void deve_retornar_avaliacao_convertida() {
		// dado
		var avaliacaoEsperada = AvaliacaoMock.geraAvaliacaoRepresentation();
		// quando
		var representation = converter.toRepresentation(AvaliacaoMock.geraAvaliacao());
		// então
		assertEquals(avaliacaoEsperada, representation);
	}

	@Test
	public void deve_retornar_avaliacao_representation_convertida() {
		// dado
		var avaliacaoEsperada = AvaliacaoMock.geraAvaliacao();
		// quando
		var domain = converter.toDomain(AvaliacaoMock.geraAvaliacaoRepresentation());
		// então
		assertEquals(avaliacaoEsperada, domain);
	}

	@Test
	public void deve_retornar_mesma_media() {
		// dado
		var mediaEsperada = AvaliacaoMock.geraAvaliacao().getMediaAvaliacao();
		// quando
		var representation = converter.toRepresentation(AvaliacaoMock.geraAvaliacao());
		// então
		assertEquals(mediaEsperada, representation.getMedia(), 0.00001);
	}
}
