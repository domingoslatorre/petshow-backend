package hyve.petshow.service.port;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ServicoDetalhadoService {
	ServicoDetalhado adicionarServicoDetalhado(ServicoDetalhado servicoDetalhado);

	List<ServicoDetalhado> buscarServicosDetalhadosPorTipoServico(Integer id) throws NotFoundException;
	
	ServicoDetalhado atualizarServicoDetalhado(Long id, Long prestadorId, ServicoDetalhado servicoDetalhadoRequest) throws BusinessException, NotFoundException;

    MensagemRepresentation removerServicoDetalhado(Long id, Long prestadorId) throws BusinessException, NotFoundException;
    
    ServicoDetalhado buscarPorId(Long id) throws NotFoundException;
    
    List<ServicoDetalhado> buscarPorPrestadorId(Long prestadorId) throws NotFoundException;
    
    ServicoDetalhado buscarPorPrestadorIdEServicoId(Long prestadorId, Long servicoId) throws NotFoundException;
}
