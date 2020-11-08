package hyve.petshow.unit.service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.service.implementation.AnimalEstimacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static hyve.petshow.mock.AnimalEstimacaoMock.animalEstimacao;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoServiceTest {
    @Mock
    private AnimalEstimacaoRepository repository;
    @InjectMocks
    private AnimalEstimacaoServiceImpl service;

    private AnimalEstimacao animalEstimacao = animalEstimacao();
    private List<AnimalEstimacao> animaisEstimacao = singletonList(animalEstimacao);

    @BeforeEach
    public void init() {
        initMocks(this);

        doReturn(animalEstimacao).when(repository).save(animalEstimacao);
        doReturn(Optional.of(animalEstimacao)).when(repository).findById(1L);
        doReturn(animaisEstimacao).when(repository).findAll();
        doReturn(animaisEstimacao).when(repository).findByDonoId(1L);
        doNothing().when(repository).delete(any(AnimalEstimacao.class));
    }

    @Test
    public void deve_retornar_animal_com_sucesso() {
        var actual = service.adicionarAnimalEstimacao(animalEstimacao);

        assertNotNull(actual);
    }

    @Test
    public void deve_trazer_animal_correto() throws NotFoundException {
        var actual = service.buscarAnimalEstimacaoPorId(1L);

        assertNotNull(actual);
    }

    @Test
    public void deve_retornar_lista_de_animais() throws NotFoundException {
        var actual = service.buscarAnimaisEstimacaoPorDono(1L);

        assertNotNull(actual);
    }

    @Test
    public void deve_retornar_animal_atualizado() throws NotFoundException, BusinessException {
        var animalEstimacaoRequest = animalEstimacao;
        animalEstimacaoRequest.setNome("arnaldo antunes");

        doReturn(animalEstimacaoRequest).when(repository).save(animalEstimacaoRequest);

        var actual = service.atualizarAnimalEstimacao(1L, animalEstimacaoRequest);

        assertEquals(animalEstimacao.getNome(), actual.getNome());
    }

    @Test
    public void deve_retornar_vazio_se_animal_nao_existir() {
        assertThrows(NotFoundException.class, () -> service.atualizarAnimalEstimacao(3L, animalEstimacao));
    }

    @Test
    public void deve_retornar_mensagem_sucesso_ao_remover_elemento() throws Exception {
        var mensagem = service.removerAnimalEstimacao(1L, 1L);

        doReturn(FALSE).when(repository).existsById(1L);

        assertAll(
                () -> assertEquals(MensagemRepresentation.MENSAGEM_SUCESSO, mensagem.getMensagem()),
                () -> assertTrue(mensagem.getSucesso()));
    }

    @Test
    public void deve_retornar_excecao_de_nao_encontrado() {
    	assertThrows(NotFoundException.class, () -> service.buscarAnimalEstimacaoPorId(3L));
    }
    
    @Test
    public void deve_retornar_excecao_por_lista_nao_encontrada() {
    	assertThrows(NotFoundException.class, () -> service.buscarAnimaisEstimacaoPorDono(2L));
    }
    
    @Test
    public void deve_retornar_excecao_por_donos_diferentes() {
    	var animalRequest = animalEstimacao();
    	animalRequest.setDonoId(2L);

    	assertThrows(BusinessException.class, () -> service.atualizarAnimalEstimacao(1L, animalRequest));
    }
    
    @Test
    public void deve_retornar_excecao_por_donos_diferentes_delecao() {
        var animalRequest = animalEstimacao();
        animalRequest.setDonoId(2L);

        assertThrows(BusinessException.class, () -> service.removerAnimalEstimacao(1L, animalRequest.getDonoId()));
    }
    
    @Test
    public void deve_retornar_mensagem_de_falha() throws BusinessException, NotFoundException {
    	doReturn(TRUE).when(repository).existsById(1L);

    	var removerAnimalEstimacao = service.removerAnimalEstimacao(1L, animalEstimacao.getDonoId());

    	assertEquals(MensagemRepresentation.MENSAGEM_FALHA, removerAnimalEstimacao.getMensagem());
    }
}
