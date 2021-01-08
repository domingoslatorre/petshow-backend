package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrecoPorTipoRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.ServicoDetalhadoTipoAnimalEstimacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ServicoDetalhadoConverter implements Converter<ServicoDetalhado, ServicoDetalhadoRepresentation>{
	@Autowired
    private ServicoConverter servicoConverter;
	@Autowired
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter;
	@Autowired
	private AdicionalConverter adicionalConverter;
	
	@Override
    public ServicoDetalhadoRepresentation toRepresentation(ServicoDetalhado domain) {
    	var representation = new ServicoDetalhadoRepresentation();
    	
    	representation.setId(domain.getId());
        representation.setTipo(servicoConverter.toRepresentation(domain.getTipo()));
        representation.setPrestadorId(domain.getPrestadorId());
        representation.setMediaAvaliacao(domain.getMediaAvaliacao());
        domain.getTiposAnimaisAceitos().stream()
            .forEach(tipoAnimalAceito -> {
                representation.getPrecoPorTipo().add(PrecoPorTipoRepresentation.builder()
                                .preco(tipoAnimalAceito.getPreco())
                                .tipoAnimal(tipoAnimalEstimacaoConverter.toRepresentation(tipoAnimalAceito.getTipoAnimalEstimacao()))
                                .build()
                );
            });

        representation.setAdicionais(adicionalConverter.toRepresentationList(domain.getAdicionais()));
        return representation;
    }

    @Override
    public ServicoDetalhado toDomain(ServicoDetalhadoRepresentation representation) {
    	var domain = new ServicoDetalhado();
        
    	domain.setId(representation.getId());
        domain.setTipo(servicoConverter.toDomain(representation.getTipo()));
        domain.setPrestadorId(representation.getPrestadorId());
        domain.setMediaAvaliacao(representation.getMediaAvaliacao());
        representation.getPrecoPorTipo().stream()
                .forEach(precoPorTipo -> domain.getTiposAnimaisAceitos().add(
                        new ServicoDetalhadoTipoAnimalEstimacao(domain,
                                tipoAnimalEstimacaoConverter.toDomain(precoPorTipo.getTipoAnimal()),
                                precoPorTipo.getPreco())
                ));
        domain.setAdicionais(adicionalConverter.toDomainList(representation.getAdicionais()));

        return domain;
    }
}
