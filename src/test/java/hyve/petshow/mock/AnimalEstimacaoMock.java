package hyve.petshow.mock;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.enums.TipoAnimalEstimacao;

import java.util.Collections;

public class AnimalEstimacaoMock {
    public static AnimalEstimacao animalEstimacao(){
        AnimalEstimacao animalEstimacao = new AnimalEstimacao();
        Cliente cliente = new Cliente();

        cliente.setAnimaisEstimacao(Collections.emptyList());

        animalEstimacao.setId(1L);
        animalEstimacao.setNome("pedrinho");
        animalEstimacao.setFoto("hahah");
        animalEstimacao.setTipo(TipoAnimalEstimacao.GATO.id());
        animalEstimacao.setDono(cliente);

        return animalEstimacao;
    }

    public static AnimalEstimacao animalEstimacaoAlt(){
        AnimalEstimacao animalEstimacao = new AnimalEstimacao();

        animalEstimacao.setId(1L);
        animalEstimacao.setNome("andrezinho");
        animalEstimacao.setFoto("aaaaa");
        animalEstimacao.setTipo(TipoAnimalEstimacao.GATO.id());

        return animalEstimacao;
    }

    public static AnimalEstimacaoRepresentation animalEstimacaoRepresentation(){
        AnimalEstimacaoRepresentation animalEstimacaoRepresentation = new AnimalEstimacaoRepresentation();

        animalEstimacaoRepresentation.setNome("pedrinho");
        animalEstimacaoRepresentation.setFoto("hahah");
        animalEstimacaoRepresentation.setTipo(TipoAnimalEstimacao.GATO);

        return animalEstimacaoRepresentation;
    }

    public static MensagemRepresentation mensagemRepresentationSucesso(){
        var mensagemRepresentation = new MensagemRepresentation(1L);

        mensagemRepresentation.setSucesso(Boolean.TRUE);

        return mensagemRepresentation;
    }
}
