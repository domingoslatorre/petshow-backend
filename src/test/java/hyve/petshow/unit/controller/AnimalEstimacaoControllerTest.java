package hyve.petshow.unit.controller;

import hyve.petshow.controller.AnimalEstimacaoController;
import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.converter.TipoAnimalEstimacaoConverter;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.mock.AnimalEstimacaoMock;
import hyve.petshow.service.port.AnimalEstimacaoService;
import hyve.petshow.service.port.TipoAnimalEstimacaoService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class AnimalEstimacaoControllerTest {
    @Autowired
    private AnimalEstimacaoController animalEstimacaoController;

    @MockBean
    private AnimalEstimacaoConverter animalEstimacaoConverter;

    @MockBean
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter;

    @MockBean
    private AnimalEstimacaoService animalEstimacaoService;

    @MockBean
    private TipoAnimalEstimacaoService tipoAnimalEstimacaoService;

    @Test
    public void deve_retornar_animal_salvo(){
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.CREATED;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.adicionarAnimalEstimacao(animalEstimacao)).thenReturn(animalEstimacao);
        when(animalEstimacaoConverter.toRepresentation(animalEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.adicionarAnimalEstimacao(animalEstimacaoRepresentation);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_obter_lista_de_animais() throws NotFoundException {
        //dado
        var expectedBody = Arrays.asList(AnimalEstimacaoMock.animalEstimacaoRepresentation());
        var expectedStatus = HttpStatus.OK;
        var animaisEstimacao = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        when(animalEstimacaoService.buscarAnimaisEstimacaoPorDono()).thenReturn(animaisEstimacao);
        when(animalEstimacaoConverter.toRepresentationList(animaisEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.buscarAnimaisEstimacao();

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_lancar_not_found_exception() throws NotFoundException {
        //dado
        var expectedStatus = HttpStatus.NO_CONTENT;

        when(animalEstimacaoService.buscarAnimaisEstimacaoPorDono()).thenThrow(NotFoundException.class);

        //entao
        assertThrows(NotFoundException.class, () -> animalEstimacaoController.buscarAnimaisEstimacao());
    }

    @Test
    public void deve_retornar_animal_atualizado() throws NotFoundException {
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.OK;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();
        var id = 1L;

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.atualizarAnimalEstimacao(id, animalEstimacao)).thenReturn(animalEstimacao);
        when(animalEstimacaoConverter.toRepresentation(animalEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.atualizarAnimalEstimacao(id, animalEstimacaoRepresentation);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_lancar_not_found_exception_quando_animal_nao_existir() throws NotFoundException {
        //dado
        var expectedStatus = HttpStatus.NO_CONTENT;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();
        var id = 1L;

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.atualizarAnimalEstimacao(id, animalEstimacao)).thenThrow(NotFoundException.class);

        //entao
        assertThrows(NotFoundException.class,
                () -> animalEstimacaoController.atualizarAnimalEstimacao(id, animalEstimacaoRepresentation));
    }

    @Test
    public void removerAnimalEstimacaoTestCase01(){
        //dado
        var id = 1L;
        var expectedStatus = HttpStatus.OK;
        var expectedBody = AnimalEstimacaoMock.mensagemRepresentationSucesso();

        when(animalEstimacaoService.removerAnimalEstimacao(id)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.removerAnimalEstimacao(id);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_retornar_falha_quando_nao_tiver_o_que_remover(){
        //dado
        var id = 1L;
        var expected = AnimalEstimacaoMock.mensagemRepresentationSucesso();

        when(animalEstimacaoService.removerAnimalEstimacao(id)).thenReturn(expected);

        //quando
        var actual = animalEstimacaoController.removerAnimalEstimacao(id);

        //entao
        assertEquals(expected, actual.getBody());
    }
}
