package hyve.petshow.service.implementation;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.port.ServicoDetalhadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static hyve.petshow.util.AuditoriaUtils.*;
import static hyve.petshow.util.ProxyUtils.verificarIdentidade;

@Service
public class ServicoDetalhadoServiceImpl implements ServicoDetalhadoService {
	private static final String SERVICO_NAO_ENCONTRADO_PARA_PRESTADOR_MENCIONADO = "SERVICO_NAO_ENCONTRADO_PARA_PRESTADOR_MENCIONADO";//"Serviço não encontrado para prestador mencionado";
	private static final String SERVICO_DETALHADO_NAO_ENCONTRADO = "SERVICO_DETALHADO_NAO_ENCONTRADO";//"Serviço detalhado não encontrado";
	private static final String NENHUM_SERVICO_DETALHADO_ENCONTRADO = "NENHUM_SERVICO_DETALHADO_ENCONTRADO";//"Nenhum serviço detalhado encontrado";
	private static final String USUARIO_NAO_PROPRIETARIO_SERVICO = "USUARIO_NAO_PROPRIETARIO_SERVICO";//"Este serviço não pertence a este usuário";

	@Autowired
	private ServicoDetalhadoRepository repository;
	
	@Override
	public ServicoDetalhado adicionarServicoDetalhado(ServicoDetalhado servicoDetalhado) {
		servicoDetalhado.setAuditoria(geraAuditoriaInsercao(Optional.of(servicoDetalhado.getPrestadorId())));

		return repository.save(servicoDetalhado);
	}

	@Override
	public Page<ServicoDetalhado> buscarServicosDetalhadosPorTipoServico(Integer id, Pageable pageable) throws NotFoundException {
		var servicosDetalhados = repository.findByTipo(id, pageable);

		if(servicosDetalhados.isEmpty()){
			throw new NotFoundException(NENHUM_SERVICO_DETALHADO_ENCONTRADO);
		}

		return servicosDetalhados;
	}

	// TODO: Ajustar metodo de atualizar para realmente atualizar | Ajustar teste
	@Override
	public ServicoDetalhado atualizarServicoDetalhado(Long id, Long prestadorId, ServicoDetalhado request)
			throws BusinessException, NotFoundException {
		var servicoDetalhado = buscarPorId(id);

		if(!verificarIdentidade(servicoDetalhado.getPrestadorId(), prestadorId)) {
			throw new BusinessException(USUARIO_NAO_PROPRIETARIO_SERVICO);
		}
		
		//servicoDetalhado.setPreco(request.getPreco());
		servicoDetalhado.setAuditoria(atualizaAuditoria(servicoDetalhado.getAuditoria(), ATIVO));
		var response = repository.save(servicoDetalhado);
		return response;
	}

	@Override
	public MensagemRepresentation removerServicoDetalhado(Long id, Long prestadorId)
			throws BusinessException, NotFoundException{
		var servicoDetalhado = buscarPorId(id);

		if(!verificarIdentidade(servicoDetalhado.getPrestadorId(), prestadorId)) {
			throw new BusinessException(USUARIO_NAO_PROPRIETARIO_SERVICO);
		}
		repository.deleteById(id);
		var sucesso = !repository.existsById(id);
		var response = new MensagemRepresentation(id);
		response.setSucesso(sucesso);
		return response;
    }

	@Override
	public ServicoDetalhado buscarPorId(Long id) throws NotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new NotFoundException(SERVICO_DETALHADO_NAO_ENCONTRADO));
	}

	@Override
	public Page<ServicoDetalhado> buscarPorPrestadorId(Long prestadorId, Pageable pageable) throws NotFoundException {
		var servicosDetalhados = repository.findByPrestadorId(prestadorId, pageable);

		if(servicosDetalhados.isEmpty()){
			throw new NotFoundException(NENHUM_SERVICO_DETALHADO_ENCONTRADO);
		}

		return servicosDetalhados;
	}

	@Override
	public ServicoDetalhado buscarPorPrestadorIdEServicoId(Long prestadorId, Long servicoId) throws NotFoundException {
		return repository.findByIdAndPrestadorId(servicoId, prestadorId).orElseThrow(() -> new NotFoundException(SERVICO_NAO_ENCONTRADO_PARA_PRESTADOR_MENCIONADO));
	}
}
