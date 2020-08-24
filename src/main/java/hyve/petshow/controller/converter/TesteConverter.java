package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.TesteRepresentation;
import hyve.petshow.domain.Teste;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TesteConverter implements Converter<Teste, TesteRepresentation> {
    @Override
    public TesteRepresentation toRepresentation(Teste teste) {
        return TesteRepresentation.builder()
                .id(teste.getId())
                .teste(teste.getTeste())
                .build();
    }

    @Override
    public Teste toDomain(TesteRepresentation testeRepresentation) {
        Teste teste = new Teste();

        teste.setId(testeRepresentation.getId());
        teste.setTeste(testeRepresentation.getTeste());

        return teste;
    }

    public List<TesteRepresentation> toRepresentationList(List<Teste> testes){
        List<TesteRepresentation> testesRepresentation = new ArrayList<>();

        testes.forEach(teste -> testesRepresentation.add(toRepresentation(teste)));

        return testesRepresentation;
    }
}
