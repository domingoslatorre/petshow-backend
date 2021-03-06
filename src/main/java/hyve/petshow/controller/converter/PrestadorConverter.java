package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrestadorConverter implements Converter<Prestador, PrestadorRepresentation> {
	@Autowired
	private ServicoDetalhadoConverter servicoConverter;
	@Autowired
	private ContaConverter contaConverter;
	@Autowired 
	private EmpresaConverter empresaConverter;

    @Override
    public PrestadorRepresentation toRepresentation(Prestador domain) {
    	var contaRepresentation = contaConverter.toRepresentation(domain);
        var representation = new PrestadorRepresentation(contaRepresentation);

        representation.setServicos(servicoConverter.toRepresentationList(domain.getServicosPrestados()));
		representation.setDescricao(domain.getDescricao());
		representation.setEmpresa(empresaConverter.toRepresentation(domain.getEmpresa()));

        return representation;
    }

    @Override
    public Prestador toDomain(PrestadorRepresentation representation) {
    	var conta = contaConverter.toDomain(representation);
        var domain = new Prestador(conta);

        domain.setServicosPrestados(servicoConverter.toDomainList(representation.getServicos()));
        domain.setDescricao(representation.getDescricao());
        domain.setEmpresa(empresaConverter.toDomain(representation.getEmpresa()));
        return domain;
    }
}
