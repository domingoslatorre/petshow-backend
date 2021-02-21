package hyve.petshow.facade;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.enums.Cargo;
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
		var conta = acessoService.adicionarConta(domain);
		
		var vinculos = domain.getVinculo();
		var vinculosSalvos = vinculos.stream().map(vinculo -> {
			var empresa = vinculo.getEmpresa();
			empresa.setDonoId(conta.getId());
			vinculo.setPrestador(new Prestador(conta));
			vinculo.setCargo(Cargo.DONO);
			empresa.getVinculos().add(vinculo);
			var empresaSalva = empresaService.salvarEmpresa(empresa);
			return vinculo;
			
		}).collect(Collectors.toList());
		
		var prestador = new Prestador(conta);
		prestador.setVinculo(vinculosSalvos);
		prestadorService.atualizarConta(prestador.getId(), prestador);
		return converter.toRepresentation(prestadorService.buscarPorId(conta.getId()));
	}
}
