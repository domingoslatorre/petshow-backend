package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.TesteValidacaoRepresentation;
import hyve.petshow.domain.TesteValidacao;
import org.springframework.stereotype.Component;

@Component
public class TesteValidacaoConverter implements Converter<TesteValidacao, TesteValidacaoRepresentation> {
    @Override
    public TesteValidacaoRepresentation toRepresentation(TesteValidacao testeValidacao) {
        return TesteValidacaoRepresentation.builder()
                .idTeste(testeValidacao.getIdTeste())
                .idValidacao(testeValidacao.getIdValidacao())
                .teste(testeValidacao.getTeste())
                .validacao(testeValidacao.getValidacao())
                .build();
    }

    @Override
    public TesteValidacao toDomain(TesteValidacaoRepresentation testeValidacaoRepresentation) {
        TesteValidacao testeValidacao = new TesteValidacao();

        testeValidacao.setIdTeste(testeValidacaoRepresentation.getIdTeste());
        testeValidacao.setIdValidacao(testeValidacaoRepresentation.getIdValidacao());
        testeValidacao.setTeste(testeValidacaoRepresentation.getTeste());
        testeValidacao.setValidacao(testeValidacaoRepresentation.getValidacao());

        return testeValidacao;
    }
}
