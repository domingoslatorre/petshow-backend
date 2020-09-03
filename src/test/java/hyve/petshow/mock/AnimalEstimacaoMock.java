package hyve.petshow.mock;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;
import hyve.petshow.domain.AnimalEstimacao;

public class AnimalEstimacaoMock {
    public static AnimalEstimacao animalEstimacao(){
        AnimalEstimacao animalEstimacao = new AnimalEstimacao();

        animalEstimacao.setId(1L);
        animalEstimacao.setNome("pedrinho");
        animalEstimacao.setFoto("hahah");
        animalEstimacao.setTipo(1);

        return animalEstimacao;
    }

    public static AnimalEstimacao animalEstimacaoAlt(){
        AnimalEstimacao animalEstimacao = new AnimalEstimacao();

        animalEstimacao.setId(1L);
        animalEstimacao.setNome("andrezinho");
        animalEstimacao.setFoto("aaaaa");
        animalEstimacao.setTipo(1);

        return animalEstimacao;
    }

    public static AnimalEstimacaoRepresentation animalEstimacaoRepresentation(){
        AnimalEstimacaoRepresentation animalEstimacaoRepresentation = new AnimalEstimacaoRepresentation();

        animalEstimacaoRepresentation.setNome("pedrinho");
        animalEstimacaoRepresentation.setFoto("hahah");
        animalEstimacaoRepresentation.setTipo(1);

        return animalEstimacaoRepresentation;
    }

    public static AnimalEstimacaoResponseRepresentation animalEstimacaoResponseRepresentation(){
        AnimalEstimacaoResponseRepresentation animalEstimacaoResponseRepresentation =
                new AnimalEstimacaoResponseRepresentation();

        animalEstimacaoResponseRepresentation.setId(1L);
        animalEstimacaoResponseRepresentation.setSucesso(Boolean.TRUE);
        animalEstimacaoResponseRepresentation.setMensagem("Operação executada com sucesso!");

        return animalEstimacaoResponseRepresentation;
    }

    public static AnimalEstimacaoResponseRepresentation animalEstimacaoResponseRepresentationAlt(){
        AnimalEstimacaoResponseRepresentation animalEstimacaoResponseRepresentation =
                new AnimalEstimacaoResponseRepresentation();

        animalEstimacaoResponseRepresentation.setId(1L);
        animalEstimacaoResponseRepresentation.setSucesso(Boolean.FALSE);
        animalEstimacaoResponseRepresentation.setMensagem("Falha durante a execução da operação.");

        return animalEstimacaoResponseRepresentation;
    }
}
