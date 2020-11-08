package hyve.petshow.mock;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.TipoAnimalEstimacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.TipoAnimalEstimacao;
import hyve.petshow.domain.embeddables.Auditoria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnimalEstimacaoMock {
    public static AnimalEstimacao animalEstimacao(){
        AnimalEstimacao animalEstimacao = new AnimalEstimacao();
        Cliente cliente = new Cliente();

        cliente.setAnimaisEstimacao(Collections.emptyList());

        animalEstimacao.setId(1L);
        animalEstimacao.setNome("pedrinho");
        animalEstimacao.setFoto("hahah");
        animalEstimacao.setTipo(tipoAnimalEstimacao());
        animalEstimacao.setAuditoria(new Auditoria());
        animalEstimacao.setDonoId(1L);

        return animalEstimacao;
    }

    public static AnimalEstimacao animalEstimacaoAlt(){
        AnimalEstimacao animalEstimacao = new AnimalEstimacao();

        animalEstimacao.setId(1L);
        animalEstimacao.setNome("andrezinho");
        animalEstimacao.setFoto("aaaaa");
        animalEstimacao.setTipo(tipoAnimalEstimacao());
        animalEstimacao.setAuditoria(new Auditoria());
        animalEstimacao.setDonoId(1L);

        return animalEstimacao;
    }

    public static AnimalEstimacaoRepresentation animalEstimacaoRepresentation(){
        AnimalEstimacaoRepresentation animalEstimacaoRepresentation = new AnimalEstimacaoRepresentation();

        animalEstimacaoRepresentation.setNome("pedrinho");
        animalEstimacaoRepresentation.setFoto("hahah");
        animalEstimacaoRepresentation.setTipo(tipoAnimalEstimacaoRepresentation());
        animalEstimacaoRepresentation.setDonoId(1L);

        return animalEstimacaoRepresentation;
    }

    public static TipoAnimalEstimacao tipoAnimalEstimacao(){
        var tipoAnimalEstimacao = new TipoAnimalEstimacao();

        tipoAnimalEstimacao.setId(1);
        tipoAnimalEstimacao.setNome("GATO");

        return tipoAnimalEstimacao;
    }

    public static TipoAnimalEstimacaoRepresentation tipoAnimalEstimacaoRepresentation(){
        var tipoAnimalEstimacaoRepresentation = new TipoAnimalEstimacaoRepresentation();

        tipoAnimalEstimacaoRepresentation.setId(1);
        tipoAnimalEstimacaoRepresentation.setNome("GATO");

        return tipoAnimalEstimacaoRepresentation;
    }

    public static List<AnimalEstimacaoRepresentation> animalEstimacaoList(){
        return Arrays.asList(animalEstimacaoRepresentation());
    }

}
