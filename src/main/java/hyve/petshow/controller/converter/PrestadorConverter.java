package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.enums.TipoConta;

// TODO: IDEM CclienteCONVERTER
public class PrestadorConverter implements Converter<Prestador, PrestadorRepresentation> {
    //	private ContaConverter contaConverter = new ContaConverter();
    private List<ServicoDetalhado> servicoDetalhadoConverter = new ArrayList<ServicoDetalhado>();
    private String descricao;

    @Override
    public PrestadorRepresentation toRepresentation(Prestador domain) {
        if(domain == null) return new PrestadorRepresentation();
//		ClienteRepresentation representation = (ClienteRepresentation) contaConverter.toRepresentation(domain);
        PrestadorRepresentation representation = new PrestadorRepresentation();
        representation.setId(domain.getId());
        representation.setCpf(domain.getCpf());
        representation.setEndereco(domain.getEndereco());
        representation.setFoto(domain.getFoto());
        representation.setLogin(domain.getLogin());
        representation.setNome(domain.getNome());
        representation.setNomeSocial(domain.getNomeSocial());
        representation.setTelefone(domain.getTelefone());
        representation.setTipo(domain.getTipo() == null ? null : domain.getTipo().getTipo());
        representation.setServicoDetalhado(servicoDetalhadoConverter.toRepresentationList(domain.getServicoDetalhado()));
        representation.descricao(domain.getDescricao());
        return representation;
    }

    @Override
    public Prestador toDomain(PrestadorRepresentation representation) {
        if(representation == null) return new Prestador();
//		Cliente domain = (Cliente) contaConverter.toDomain(representation);
        Cliente domain = new Cliente();
        domain.setCpf(representation.getCpf());
        domain.setEndereco(representation.getEndereco());
        domain.setFoto(representation.getFoto());
        domain.setId(representation.getId());
        domain.setLogin(representation.getLogin());
        domain.setNome(representation.getNome());
        domain.setNomeSocial(representation.getNomeSocial());
        domain.setTelefone(representation.getTelefone());
        domain.setTipo(TipoConta.getTipoByInteger(representation.getTipo()));
        domain.setServicoDetalhado(servicoDetalhadoConverter.toDomainList(representation.getServicoDetalhado()));
        domain.descricao(domain.getDescricao());
        return domain;
    }

}
