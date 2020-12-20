package hyve.petshow.service.port;

import java.util.List;

import hyve.petshow.domain.Adicional;

public interface AdicionalService {
	List<Adicional> buscarPorServicoDetalhado(Long idServico) throws Exception;
	Adicional buscarPorId(Long idAdicional) throws Exception;
	Adicional criarAdicional(Adicional adicional);
	Adicional atualizarAdicional(Long idAdicional, Adicional adicional) throws Exception;
	Boolean desativarAdicional(Long idAdicional) throws Exception;
	List<Adicional> criarAdicionais(List<Adicional> adicionais);
}
