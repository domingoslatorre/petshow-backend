package hyve.petshow.unit.controller;

import hyve.petshow.controller.AnimalEstimacaoController;
import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.mock.AnimalEstimacaoMock;
import hyve.petshow.service.port.AnimalEstimacaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AnimalEstimacaoControllerTest {
    @Autowired
    private AnimalEstimacaoController animalEstimacaoController;

    @MockBean
    private AnimalEstimacaoConverter animalEstimacaoConverter;

    @MockBean(name = "animalEstimacaoService")
    private AnimalEstimacaoService animalEstimacaoService;

    @Test
    public void criarAnimalEstimacaoTestCase01(){
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.CREATED;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.criarAnimalEstimacao(animalEstimacao)).thenReturn(animalEstimacao);
        when(animalEstimacaoConverter.toRepresentation(animalEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.criarAnimalEstimacao(animalEstimacaoRepresentation);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void obterAnimalEstimacaoTestCase01(){
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.OK;
        var animalEstimacao = Optional.of(AnimalEstimacaoMock.animalEstimacao());
        var id = 1L;

        when(animalEstimacaoService.obterAnimalEstimacaoPorId(id)).thenReturn(animalEstimacao);
        when(animalEstimacaoConverter.toRepresentation(animalEstimacao.get())).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.obterAnimalEstimacao(id);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void obterAnimalEstimacaoTestCase02(){
        var expectedStatus = HttpStatus.NO_CONTENT;
        Optional<AnimalEstimacao> animalEstimacao = Optional.empty();
        var id = 1L;

        when(animalEstimacaoService.obterAnimalEstimacaoPorId(id)).thenReturn(animalEstimacao);

        //quando
        var actual = animalEstimacaoController.obterAnimalEstimacao(id);

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }

    @Test
    public void obterAnimaisEstimacaoTestCase01(){
        //dado
        var expectedBody = Arrays.asList(AnimalEstimacaoMock.animalEstimacaoRepresentation());
        var expectedStatus = HttpStatus.OK;
        var animaisEstimacao = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        when(animalEstimacaoService.obterAnimaisEstimacao()).thenReturn(animaisEstimacao);
        when(animalEstimacaoConverter.toRepresentationList(animaisEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.obterAnimaisEstimacao();

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void obterAnimaisEstimacaoTestCase02(){
        //dado
        var expectedStatus = HttpStatus.NO_CONTENT;
        List<AnimalEstimacao> animaisEstimacao = Collections.emptyList();

        when(animalEstimacaoService.obterAnimaisEstimacao()).thenReturn(animaisEstimacao);

        //quando
        var actual = animalEstimacaoController.obterAnimaisEstimacao();

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }

    @Test
    public void atualizarAnimalEstimacaoTestCase01(){
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.OK;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();
        var id = 1L;

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.atualizarAnimalEstimacao(id, animalEstimacao)).thenReturn(Optional.of(animalEstimacao));
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
    public void atualizarAnimalEstimacaoTestCase02(){
        //dado
        var expectedStatus = HttpStatus.NO_CONTENT;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();
        var id = 1L;

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.atualizarAnimalEstimacao(id, animalEstimacao)).thenReturn(Optional.empty());

        //quando
        var actual = animalEstimacaoController.atualizarAnimalEstimacao(id, animalEstimacaoRepresentation);

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }

    @Test
    public void removerAnimalEstimacaoTestCase01(){
        //dado
        var id = 1L;
        var expectedStatus = HttpStatus.OK;
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoResponseRepresentation();

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
    public void removerAnimalEstimacaoTestCase02(){
        //dado
        var id = 1L;
        var expectedStatus = HttpStatus.NO_CONTENT;
        var animalEstimacaoResponseRepresentation = AnimalEstimacaoMock.animalEstimacaoResponseRepresentationAlt();

        when(animalEstimacaoService.removerAnimalEstimacao(id)).thenReturn(animalEstimacaoResponseRepresentation);

        //quando
        var actual = animalEstimacaoController.removerAnimalEstimacao(id);

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }
}
