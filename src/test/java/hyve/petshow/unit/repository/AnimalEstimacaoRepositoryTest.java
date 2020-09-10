package hyve.petshow.unit.repository;

import hyve.petshow.mock.AnimalEstimacaoMock;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoRepositoryTest {
    @Autowired
    private AnimalEstimacaoRepository animalEstimacaoRepository;

    @Test
    @BeforeAll
    public void deve_salvar_dados(){
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacao();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();

        //quando
        var actual = animalEstimacaoRepository.save(animalEstimacao);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_encontrar_dados(){
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacao();
        var id = 1L;

        //quando
        var actual = animalEstimacaoRepository.findById(id).get();

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_lista(){
        //dado
        var expected = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        //quando
        var actual = animalEstimacaoRepository.findAll();

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_atualizar_animal(){
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacaoAlt();
        var id = 1L;
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacaoAlt();
        var request = animalEstimacaoRepository.findById(id).get();

        request.setNome(animalEstimacao.getNome());
        request.setFoto(animalEstimacao.getFoto());
        request.setTipo(animalEstimacao.getTipo());

        //quando
        var actual = animalEstimacaoRepository.save(request);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    @AfterAll
    public void deve_deletar_animal(){
        //dado
        var id = 1L;
        var expected = Optional.empty();

        //quando
        animalEstimacaoRepository.deleteById(id);
        var actual = animalEstimacaoRepository.findById(id);

        //entao
        assertEquals(expected, actual);
    }
}
