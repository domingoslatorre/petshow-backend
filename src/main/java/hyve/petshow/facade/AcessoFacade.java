package hyve.petshow.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.enums.Cargo;
import hyve.petshow.domain.enums.TipoConta;
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
		if(TipoConta.PRESTADOR_AUTONOMO.equals(domain.getTipo())) {
			var representation = new Prestador(domain);
			return salvaPrestador(representation);
		}
		
		return contaConverter.toRepresentation(acessoService.adicionarConta(domain));
	}

	public PrestadorRepresentation salvaPrestador(Prestador domain) throws Exception {
		var prestador = acessoService.adicionarConta(domain);
		
		var vinculos = domain.getVinculo();
		vinculos.forEach(vinculo -> {
			var empresa = vinculo.getEmpresa();
			empresa.setDonoId(prestador.getId());
			var empresaSalva = empresaService.salvarEmpresa(empresa);
			vinculo.setEmpresa(empresaSalva);
			vinculo.setCargo(Cargo.DONO);
		});
		return converter.toRepresentation(prestadorService.buscarPorId(prestador.getId()));
	}
}
