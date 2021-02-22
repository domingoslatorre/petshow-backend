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
		var prestador = new Prestador(conta);
		domain.getVinculo().forEach(vinculo -> {
			vinculo.setPrestador(prestador); 
			vinculo.setEmpresa(empresaService.salvarEmpresa(vinculo.getEmpresa()));
			vinculo.setCargo(Cargo.DONO);
		});
		prestador.setVinculo(domain.getVinculo());
		prestadorService.atualizarConta(prestador.getId(), prestador);
//		var vinculos = domain.getVinculo();
//		var vinculosMap = vinculos.stream().map(vinculo -> {
//			vinculo.getEmpresa().setDonoId(prestador.getId());
//			empresaService.salvarEmpresa(vinculo.getEmpresa());
//			vinculo.setPrestador(prestador);
//			vinculo.setCargo(Cargo.DONO);
//			return vinculo;
//		}).collect(Collectors.toList());
		
//		prestador.setVinculo(vinculosMap);
//		prestadorService.atualizarConta(prestador.getId(), prestador);
		return converter.toRepresentation(prestadorService.buscarPorId(conta.getId()));
	}
}
