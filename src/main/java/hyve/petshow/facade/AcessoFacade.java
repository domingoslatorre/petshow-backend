package hyve.petshow.facade;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.service.port.AcessoService;
import hyve.petshow.service.port.EmpresaService;
import hyve.petshow.service.port.PrestadorService;

@Component
public class AcessoFacade {
	@Autowired
	private PrestadorService prestadorService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private AcessoService acessoService;
	@Autowired
	private PrestadorConverter converter;
	@Autowired
	private ContaConverter contaConverter;

	public ContaRepresentation salvaConta(ContaRepresentation conta) throws Exception {
		var domain = contaConverter.toDomain(conta);
		return contaConverter.toRepresentation(acessoService.adicionarConta(domain));
	}

	public PrestadorRepresentation salvaPrestador(PrestadorRepresentation representation) throws Exception {
		var domain = converter.toDomain(representation);
		var conta = new Prestador(acessoService.adicionarConta(domain));
		var empresa = domain.getEmpresa();
		conta.setEmpresa(empresa);
		empresa.setDono(conta);
		empresaService.salvarEmpresa(empresa);
//		prestadorService.atualizarConta(conta.getId(), conta);
		return converter.toRepresentation(prestadorService.buscarPorId(conta.getId()));
	}
}
