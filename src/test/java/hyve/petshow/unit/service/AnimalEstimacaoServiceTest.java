package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.mock.AnimalEstimacaoMock;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.service.port.AnimalEstimacaoService;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class AnimalEstimacaoServiceTest {

	@Autowired
    private AnimalEstimacaoService animalEstimacaoService;

    @MockBean
    private AnimalEstimacaoRepository animalEstimacaoRepository;

    @Test
    public void deve_retornar_animal_com_sucesso() {
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
    public void deve_trazer_animal_correto() {
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
    public void deve_retornar_lista_de_animais() {
        //dado
        var expected = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        when(animalEstimacaoRepository.findAll()).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.obterAnimaisEstimacao();

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_animal_atualizado(){
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
    public void deve_retornar_vazio_se_animal_nao_existir(){
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
    public void deve_remover_animal_de_estimacao(){
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
