package hyve.petshow.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.CriteriosAvaliacao;
import hyve.petshow.mock.entidades.AvaliacaoMock;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AvaliacaoTest {
	@Test
	public void deve_ter_metodos_implementados() {
		// dado
		final Class<Avaliacao> avaliacao = Avaliacao.class;

		// entao
		assertPojoMethodsFor(avaliacao).areWellImplemented();
	}

	@Test
	public void deve_retornar_media_5() {
		// dado
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();

		// quando
		Double media = avaliacao.getMediaAvaliacao();

		// então
		assertEquals(5.0, media, 0.0001);
	}

	@Test
	public void deve_retornar_media_0() {
		// dado
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
		CriteriosAvaliacao avaliacaoInfo = new CriteriosAvaliacao();
		avaliacao.setAvaliacaoInfo(avaliacaoInfo); // Deixa a avaliação com as informações nulas

		// quando
		Double media = avaliacao.getMediaAvaliacao();

		// entao
		assertEquals(0.0, media, 0.0001);
	}

	@Test
	public void deve_retornar_media_0_por_informacoes_nulas() {
		// Dado
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
		avaliacao.setAvaliacaoInfo(null);

		// Quando
		Double media = avaliacao.getMediaAvaliacao();

		// Então
		assertEquals(0.0, media, 0.0001);

	}
}
