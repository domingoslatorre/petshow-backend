package hyve.petshow.unit.service;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.mock.AnimalEstimacaoMock;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.service.port.AnimalEstimacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AnimalEstimacaoServiceTest {
    @Autowired
    private AnimalEstimacaoService animalEstimacaoService;

    @MockBean
    private AnimalEstimacaoRepository animalEstimacaoRepository;

    @Test
    public void criarAnimalEstimacaoTestCase01() {
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacao();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();

        when(animalEstimacaoRepository.save(animalEstimacao)).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.criarAnimalEstimacao(animalEstimacao);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void obterAnimalEstimacaoPorIdTestCase01() {
        //dado
        var expected = Optional.of(AnimalEstimacaoMock.animalEstimacao());
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.obterAnimalEstimacaoPorId(id);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void obterAnimaisEstimacaoTestCase01() {
        //dado
        var expected = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        when(animalEstimacaoRepository.findAll()).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.obterAnimaisEstimacao();

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void atualizarAnimalEstimacaoTestCase01(){
        //dado
        var animalEstimacao = Optional.of(AnimalEstimacaoMock.animalEstimacao());
        var requestBody = AnimalEstimacaoMock.animalEstimacaoAlt();
        var expected = Optional.of(requestBody);
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(animalEstimacao);
        when(animalEstimacaoRepository.save(requestBody)).thenReturn(requestBody);

        //quando
        var actual = animalEstimacaoService.atualizarAnimalEstimacao(id, requestBody);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void atualizarAnimalEstimacaoTestCase02(){
        //dado
        Optional<AnimalEstimacao> animalEstimacao = Optional.empty();
        var requestBody = AnimalEstimacaoMock.animalEstimacaoAlt();
        var expected = Optional.empty();
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(animalEstimacao);

        //quando
        var actual = animalEstimacaoService.atualizarAnimalEstimacao(id, requestBody);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void removerAnimalEstimacaoTestCase01(){
        //dado
        var id = 1L;
        var expected = AnimalEstimacaoMock.animalEstimacaoResponseRepresentation();

        when(animalEstimacaoRepository.existsById(id)).thenReturn(Boolean.FALSE);

        //quando
        var actual = animalEstimacaoService.removerAnimalEstimacao(id);

        //entao
        assertEquals(expected, actual);
    }

}
