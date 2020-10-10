package hyve.petshow.unit.service;

import static hyve.petshow.mock.AnimalEstimacaoMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
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
        var id = 1L;

        when(animalEstimacaoRepository.findAll()).thenReturn(expected);
        when(animalEstimacaoRepository.findByDonoId(Mockito.anyLong())).thenReturn(expected);

        //quando
        var actual = animalEstimacaoService.buscarAnimaisEstimacaoPorDono(id);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_animal_atualizado() throws NotFoundException, BusinessException {
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
        var id = 1L;

        when(animalEstimacaoRepository.findById(id)).thenReturn(animalEstimacao);

        //entao
        assertThrows(NotFoundException.class, () -> {
            animalEstimacaoService.atualizarAnimalEstimacao(id, requestBody);
        });
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
        
        
		when(animalEstimacaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(animalEstimacao));
        when(animalEstimacaoRepository.existsById(id)).thenReturn(Boolean.FALSE);

        //quando
        var actual = animalEstimacaoService.removerAnimalEstimacao(id, donoId);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_excecao_de_nao_encontrado() {
    	Mockito.when(animalEstimacaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    	assertThrows(NotFoundException.class, () -> {
    		animalEstimacaoService.buscarAnimalEstimacaoPorId(1l);
    	});
    }
    
    @Test
    public void deve_retornar_excecao_por_lista_nao_encontrada() {
    	Mockito.when(animalEstimacaoRepository.findByDonoId(Mockito.anyLong())).thenReturn(new ArrayList<>());
    	
    	assertThrows(NotFoundException.class, () -> {
    		animalEstimacaoService.buscarAnimaisEstimacaoPorDono(1l);
    	});
    }
    
    @Test
    public void deve_retornar_excecao_por_donos_diferentes() {
    	AnimalEstimacao animalRequest = new AnimalEstimacao();
    	AnimalEstimacao animalDb = new AnimalEstimacao();
    	
    	animalRequest.setDonoId(1l);
    	animalRequest.setDonoId(2l);
    	
    	Mockito.when(animalEstimacaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(animalDb));
    	
    	assertThrows(BusinessException.class, () -> {
    		animalEstimacaoService.atualizarAnimalEstimacao(2l, animalRequest);
    	});
    }
    
    @Test
    public void deve_retornar_excecao_por_donos_diferentes_delecao() {
    	AnimalEstimacao animalRequest = new AnimalEstimacao();
    	AnimalEstimacao animalDb = new AnimalEstimacao();
    	
    	animalRequest.setDonoId(1l);
    	animalRequest.setDonoId(2l);
    	
    	Mockito.when(animalEstimacaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(animalDb));
    	
    	assertThrows(BusinessException.class, () -> {
    		animalEstimacaoService.removerAnimalEstimacao(2l, animalRequest.getDonoId());
    	});
    }
    
    @Test
    public void deve_retornar_mensagem_de_falha() throws BusinessException, NotFoundException {
    	AnimalEstimacao animalRequest = new AnimalEstimacao();
    	animalRequest.setDonoId(1l);
    	
    	Mockito.when(animalEstimacaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(animalRequest));
    	Mockito.when(animalEstimacaoRepository.existsById(Mockito.anyLong())).thenReturn(true);
    	
    	var removerAnimalEstimacao = animalEstimacaoService.removerAnimalEstimacao(1l, animalRequest.getDonoId());
    	assertEquals(MensagemRepresentation.MENSAGEM_FALHA, removerAnimalEstimacao.getMensagem());
    	
    	
    }
}
