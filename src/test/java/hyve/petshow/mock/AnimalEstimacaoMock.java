package hyve.petshow.mock;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.TipoAnimalEstimacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.TipoAnimalEstimacao;

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
        animalEstimacao.setDono(cliente);

        return animalEstimacao;
    }

    public static AnimalEstimacao animalEstimacaoAlt(){
        AnimalEstimacao animalEstimacao = new AnimalEstimacao();

        animalEstimacao.setId(1L);
        animalEstimacao.setNome("andrezinho");
        animalEstimacao.setFoto("aaaaa");
        animalEstimacao.setTipo(tipoAnimalEstimacao());

        return animalEstimacao;
    }

    public static AnimalEstimacaoRepresentation animalEstimacaoRepresentation(){
        AnimalEstimacaoRepresentation animalEstimacaoRepresentation = new AnimalEstimacaoRepresentation();

        animalEstimacaoRepresentation.setNome("pedrinho");
        animalEstimacaoRepresentation.setFoto("hahah");
        animalEstimacaoRepresentation.setTipo(tipoAnimalEstimacaoRepresentation());

        return animalEstimacaoRepresentation;
    }

    public static MensagemRepresentation mensagemRepresentationSucesso(){
        var mensagemRepresentation = new MensagemRepresentation(1L);

        mensagemRepresentation.setSucesso(Boolean.TRUE);

        return mensagemRepresentation;
    }

    public static MensagemRepresentation mensagemRepresentationFalha(){
        var mensagemRepresentation = new MensagemRepresentation(1L);

        mensagemRepresentation.setSucesso(Boolean.FALSE);

        return mensagemRepresentation;
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
