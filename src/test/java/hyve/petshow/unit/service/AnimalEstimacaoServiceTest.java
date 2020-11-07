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
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static hyve.petshow.mock.AnimalEstimacaoMock.*;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoServiceTest {
    @Mock
    private AnimalEstimacaoRepository animalEstimacaoRepository;

    @InjectMocks
    private AnimalEstimacaoServiceImpl service;

    @BeforeEach
    public void init() {
        initMocks(this);
    }

    @Test
    public void deve_retornar_animal_com_sucesso() {
        //dado
        var expected = animalEstimacao();
        var animalEstimacao = animalEstimacao();

        when(animalEstimacaoRepository.save(animalEstimacao)).thenReturn(expected);

        //quando
        var actual = service.adicionarAnimalEstimacao(animalEstimacao);

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
        var actual = service.buscarAnimalEstimacaoPorId(id);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_lista_de_animais() throws NotFoundException {
        //dado
        var expected = singletonList(animalEstimacao());
        var id = 1L;

        when(animalEstimacaoRepository.findAll()).thenReturn(expected);
        when(animalEstimacaoRepository.findByDonoId(anyLong())).thenReturn(expected);

        //quando
        var actual = service.buscarAnimaisEstimacaoPorDono(id);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_animal_atualizado() throws NotFoundException, BusinessException {
        //dado
        var animalEstimacao = Optional.of(animalEstimacao());
        AnimalEstimacao requestBody = animalEstimacaoAlt();
        var id = 1L;

        when(animalEstimacaoRepository.findById(anyLong())).thenReturn(animalEstimacao);
        when(animalEstimacaoRepository.save(any(AnimalEstimacao.class))).thenReturn(requestBody);

        //quando
        var actual = service.atualizarAnimalEstimacao(id, requestBody);

        //entao
        assertEquals(requestBody, actual);
    }

    @Test
    public void deve_retornar_vazio_se_animal_nao_existir() {
        //dado
        Optional<AnimalEstimacao> animalEstimacao = Optional.empty();
        var requestBody = animalEstimacaoAlt();
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(animalEstimacao);

        //entao
        assertThrows(NotFoundException.class, () -> service.atualizarAnimalEstimacao(id, requestBody));
    }

    @Test
    public void deve_remover_animal_de_estimacao() throws NotFoundException, BusinessException {
        //dado
        var id = 1L;
        var donoId = 1L;
        var expected = mensagemRepresentationSucesso();
        var animalEstimacao = new AnimalEstimacao();
        animalEstimacao.setId(id);
        animalEstimacao.setDonoId(donoId);
        
        
		when(animalEstimacaoRepository.findById(anyLong())).thenReturn(Optional.of(animalEstimacao));
        when(animalEstimacaoRepository.existsById(id)).thenReturn(Boolean.FALSE);

        //quando
        var actual = service.removerAnimalEstimacao(id, donoId);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_excecao_de_nao_encontrado() {
    	Mockito.when(animalEstimacaoRepository.findById(anyLong())).thenReturn(Optional.empty());
    	assertThrows(NotFoundException.class, () -> service.buscarAnimalEstimacaoPorId(1L));
    }
    
    @Test
    public void deve_retornar_excecao_por_lista_nao_encontrada() {
    	Mockito.when(animalEstimacaoRepository.findByDonoId(anyLong())).thenReturn(new ArrayList<>());
    	
    	assertThrows(NotFoundException.class, () -> service.buscarAnimaisEstimacaoPorDono(1L));
    }
    
    @Test
    public void deve_retornar_excecao_por_donos_diferentes() {
    	AnimalEstimacao animalRequest = new AnimalEstimacao();
    	AnimalEstimacao animalDb = new AnimalEstimacao();
    	
    	animalRequest.setDonoId(1L);
        animalDb.setDonoId(2L);
    	
    	Mockito.when(animalEstimacaoRepository.findById(anyLong())).thenReturn(Optional.of(animalDb));
    	
    	assertThrows(BusinessException.class, () -> service.atualizarAnimalEstimacao(2L, animalRequest));
    }
    
    @Test
    public void deve_retornar_excecao_por_donos_diferentes_delecao() {
    	AnimalEstimacao animalRequest = new AnimalEstimacao();
    	AnimalEstimacao animalDb = new AnimalEstimacao();
    	
    	animalRequest.setDonoId(1L);
        animalDb.setDonoId(2L);
    	
    	Mockito.when(animalEstimacaoRepository.findById(anyLong())).thenReturn(Optional.of(animalDb));
    	
    	assertThrows(BusinessException.class, () -> service.removerAnimalEstimacao(2l, animalRequest.getDonoId()));
    }
    
    @Test
    public void deve_retornar_mensagem_de_falha() throws BusinessException, NotFoundException {
    	AnimalEstimacao animalRequest = new AnimalEstimacao();
    	animalRequest.setDonoId(1L);
    	
    	Mockito.when(animalEstimacaoRepository.findById(anyLong())).thenReturn(Optional.of(animalRequest));
    	Mockito.when(animalEstimacaoRepository.existsById(anyLong())).thenReturn(true);
    	
    	var removerAnimalEstimacao = service.removerAnimalEstimacao(1L, animalRequest.getDonoId());
    	assertEquals(MensagemRepresentation.MENSAGEM_FALHA, removerAnimalEstimacao.getMensagem());
    	
    	
    }
}
