package hyve.petshow.unit.controller;

import hyve.petshow.controller.AnimalEstimacaoController;
import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.mock.AnimalEstimacaoMock;
import hyve.petshow.mock.MensagemMock;
import hyve.petshow.service.port.AnimalEstimacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static hyve.petshow.mock.AnimalEstimacaoMock.*;
import static hyve.petshow.mock.MensagemMock.mensagemRepresentationFalha;
import static hyve.petshow.mock.MensagemMock.mensagemRepresentationSucesso;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoControllerTest {
	@Mock
	private AnimalEstimacaoConverter converter;
	@Mock
	private AnimalEstimacaoService service;
	@InjectMocks
	private AnimalEstimacaoController controller;

	private AnimalEstimacaoRepresentation animalEstimacaoRepresentation = animalEstimacaoRepresentation();
	private AnimalEstimacao animalEstimacao = animalEstimacao();

	private List<AnimalEstimacaoRepresentation> animalEstimacaoRepresentationList = singletonList(animalEstimacaoRepresentation);
	private List<AnimalEstimacao> animalEstimacaoList = singletonList(animalEstimacao);

	private MensagemRepresentation mensagemSucesso = mensagemRepresentationSucesso();
	private MensagemRepresentation mensagemFalha = mensagemRepresentationFalha();

	@BeforeEach
	public void init() throws NotFoundException, BusinessException {
		initMocks(this);

		doReturn(animalEstimacao).when(converter).toDomain(any(AnimalEstimacaoRepresentation.class));
		doReturn(animalEstimacaoRepresentation).when(converter).toRepresentation(any(AnimalEstimacao.class));
		doReturn(animalEstimacaoRepresentationList).when(converter).toRepresentationList(anyList());
		doReturn(animalEstimacao).when(service).adicionarAnimalEstimacao(any(AnimalEstimacao.class));
		doReturn(animalEstimacaoList).when(service).buscarAnimaisEstimacaoPorDono(1L);
		doThrow(NotFoundException.class).when(service).buscarAnimaisEstimacaoPorDono(2L);
		doReturn(animalEstimacao).when(service).atualizarAnimalEstimacao(anyLong(), any(AnimalEstimacao.class));
		doThrow(NotFoundException.class).when(service).atualizarAnimalEstimacao(2L, animalEstimacao);
		doReturn(mensagemSucesso).when(service).removerAnimalEstimacao(1L, 1L);
		doReturn(mensagemFalha).when(service).removerAnimalEstimacao(2L, 2L);
	}

	@Test
	public void deve_retornar_animal_salvo() {
		var expected = ResponseEntity.status(HttpStatus.CREATED).body(animalEstimacaoRepresentation);

		var actual = controller.adicionarAnimalEstimacao(animalEstimacaoRepresentation);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_obter_lista_de_animais() throws NotFoundException {
		var expected = ResponseEntity.status(HttpStatus.OK).body(animalEstimacaoRepresentationList);

		var actual = controller.buscarAnimaisEstimacao(1L);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_lancar_not_found_exception() {
		assertThrows(NotFoundException.class, () -> controller.buscarAnimaisEstimacao(2L));
	}

	@Test
	public void deve_retornar_animal_atualizado() throws NotFoundException, BusinessException {
		var expected = ResponseEntity.status(HttpStatus.OK).body(animalEstimacaoRepresentation);

		var actual = controller.atualizarAnimalEstimacao(1L, animalEstimacaoRepresentation);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_lancar_not_found_exception_quando_animal_nao_existir() {
		assertThrows(NotFoundException.class,
				() -> controller.atualizarAnimalEstimacao(2L, animalEstimacaoRepresentation));
	}

	@Test
	public void removerAnimalEstimacaoTestCase01() throws Exception {
		var expected = ResponseEntity.status(HttpStatus.OK).body(mensagemSucesso);

		var actual = controller.removerAnimalEstimacao(1L, 1L);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_retornar_falha_quando_nao_tiver_o_que_remover() throws Exception {
		var expected = ResponseEntity.status(HttpStatus.OK).body(mensagemFalha);

		var actual = controller.removerAnimalEstimacao(2L, 2L);

		assertEquals(expected, actual);
	}
}
