package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrecoPorTipoRepresentation;
import hyve.petshow.domain.ServicoDetalhadoTipoAnimalEstimacao;
import hyve.petshow.domain.embeddables.Auditoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static hyve.petshow.util.AuditoriaUtils.INATIVO;
import static hyve.petshow.util.NullUtils.isNotNull;

@Component
public class ServicoDetalhadoTipoAnimalEstimacaoConverter
        implements Converter<ServicoDetalhadoTipoAnimalEstimacao, PrecoPorTipoRepresentation> {
    @Autowired
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter;

    @Override
    public PrecoPorTipoRepresentation toRepresentation(ServicoDetalhadoTipoAnimalEstimacao domain) {
        var representation = new PrecoPorTipoRepresentation();

        representation.setTipoAnimal(tipoAnimalEstimacaoConverter.toRepresentation(domain.getTipoAnimalEstimacao()));
        representation.setPreco(domain.getPreco());
        representation.setAtivo(domain.getAuditoria().isAtivo());

        return representation;
    }

    @Override
    public ServicoDetalhadoTipoAnimalEstimacao toDomain(PrecoPorTipoRepresentation representation) {
        var domain = new ServicoDetalhadoTipoAnimalEstimacao();

        domain.setTipoAnimalEstimacao(tipoAnimalEstimacaoConverter.toDomain(representation.getTipoAnimal()));
        domain.setPreco(representation.getPreco());

        if(isNotNull(representation.getAtivo())){
            var auditoria = new Auditoria();
            auditoria.setFlagAtivo(representation.getAtivo() ? ATIVO : INATIVO);
            domain.setAuditoria(auditoria);
        }

        return domain;
    }
}
