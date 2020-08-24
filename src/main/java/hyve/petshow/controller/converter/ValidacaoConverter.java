package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.TesteRepresentation;
import hyve.petshow.controller.representation.ValidacaoRepresentation;
import hyve.petshow.domain.Teste;
import hyve.petshow.domain.Validacao;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidacaoConverter implements Converter<Validacao, ValidacaoRepresentation> {
    @Override
    public ValidacaoRepresentation toRepresentation(Validacao validacao) {
        return ValidacaoRepresentation.builder()
                .id(validacao.getId())
                .validacao(validacao.getValidacao())
                .build();
    }

    @Override
    public Validacao toDomain(ValidacaoRepresentation validacaoRepresentation) {
        Validacao validacao = new Validacao();

        validacao.setId(validacaoRepresentation.getId());
        validacao.setValidacao(validacaoRepresentation.getValidacao());

        return validacao;
    }

    public List<ValidacaoRepresentation> toRepresentationList(List<Validacao> validacoes){
        List<ValidacaoRepresentation> validacoesRepresentation = new ArrayList<>();

        validacoes.forEach(validacao -> validacoesRepresentation.add(toRepresentation(validacao)));

        return validacoesRepresentation;
    }
}
