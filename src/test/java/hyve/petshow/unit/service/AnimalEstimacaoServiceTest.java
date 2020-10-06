package hyve.petshow.unit.service;

import static hyve.petshow.mock.AnimalEstimacaoMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import hyve.petshow.exceptions.NotFoundException;
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
        var expected = animalEstimacao();
        var animalEstimacao = animalEstimacao();

        when(animalEstimacaoRepository.save(animalEstimacao)).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.adicionarAnimalEstimacao(animalEstimacao);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_trazer_animal_correto() throws NotFoundException {
        //dado
        var repositoryResponse = Optional.of(animalEstimacao());
        var expected = repositoryResponse.get();
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(repositoryResponse);

        //quando
        var actual = animalEstimacaoService.buscarAnimalEstimacaoPorId(id);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_lista_de_animais() throws NotFoundException {
        //dado
        var expected = Arrays.asList(animalEstimacao());

        when(animalEstimacaoRepository.findAll()).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.buscarAnimaisEstimacao();

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_animal_atualizado() throws NotFoundException {
        //dado
        var animalEstimacao = Optional.of(animalEstimacao());
        AnimalEstimacao requestBody = animalEstimacaoAlt();
        var expected = requestBody;
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(animalEstimacao);
        when(animalEstimacaoRepository.save(requestBody)).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.atualizarAnimalEstimacao(id, requestBody);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_vazio_se_animal_nao_existir() throws NotFoundException {
        //dado
        Optional<AnimalEstimacao> animalEstimacao = Optional.empty();
        var requestBody = animalEstimacaoAlt();
        var expected = Optional.empty();
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(animalEstimacao);

        //entao
        assertThrows(NotFoundException.class, () -> {
            animalEstimacaoService.atualizarAnimalEstimacao(id, requestBody);
        });
    }

    @Test
    public void deve_remover_animal_de_estimacao(){
        //dado
        var id = 1L;
        var expected = mensagemRepresentationSucesso();

        when(animalEstimacaoRepository.existsById(id)).thenReturn(Boolean.FALSE);

        //quando
        var actual = animalEstimacaoService.removerAnimalEstimacao(id);

        //entao
        assertEquals(expected, actual);
    }

}
