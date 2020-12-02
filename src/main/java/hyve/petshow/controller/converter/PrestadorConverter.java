package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrestadorConverter implements Converter<Prestador, PrestadorRepresentation> {
	@Autowired
	private ServicoDetalhadoConverter servicoConverter = new ServicoDetalhadoConverter();
	@Autowired
	private ContaConverter contaConverter = new ContaConverter();

    @Override
    public PrestadorRepresentation toRepresentation(Prestador domain) {
    	var contaRepresentation = contaConverter.toRepresentation(domain);
        var representation = new PrestadorRepresentation(contaRepresentation);
        representation.setServicos(servicoConverter.toRepresentationList(domain.getServicosPrestados()));
		representation.setDescricao(domain.getDescricao());
//        var login = new Login(domain.getLogin().getEmail());
//
//        representation.setNomeSocial(domain.getNomeSocial());
//        representation.setCpf(domain.getCpf());
//        representation.setEndereco(domain.getEndereco());
//        representation.setFoto(domain.getFoto());
//        representation.setId(domain.getId());
//        representation.setLogin(login);
//		representation.setNome(domain.getNome());
//		representation.setNomeSocial(domain.getNomeSocial());
//		representation.setTelefone(domain.getTelefone());
//		representation.setTipo(domain.getTipo() == null ? null : domain.getTipo().getTipo());
//        representation.setIsAtivo(domain.isAtivo());
        

        return representation;
    }

    @Override
    public Prestador toDomain(PrestadorRepresentation representation) {
    	var conta = contaConverter.toDomain(representation);
        var domain = new Prestador(conta);

//        domain.setNomeSocial(representation.getNomeSocial());
//        domain.setCpf(representation.getCpf());
//		domain.setEndereco(representation.getEndereco());
//		domain.setFoto(representation.getFoto());
//		domain.setId(representation.getId());
//		domain.setLogin(representation.getLogin());
//		domain.setNome(representation.getNome());
//		domain.setNomeSocial(representation.getNomeSocial());
//		domain.setTelefone(representation.getTelefone());
//		domain.setTipo(TipoConta.getTipoByInteger(representation.getTipo()));
        domain.setServicosPrestados(servicoConverter.toDomainList(representation.getServicos()));
        domain.setDescricao(representation.getDescricao());

        return domain;
    }
}
