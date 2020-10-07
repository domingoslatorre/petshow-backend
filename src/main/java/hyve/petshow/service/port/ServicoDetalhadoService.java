package hyve.petshow.service.port;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.BusinessException;

import java.util.List;

import hyve.petshow.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.ServicoDetalhado;


@Service
public interface ServicoDetalhadoService {
	ServicoDetalhado adicionarServicoDetalhado(ServicoDetalhado servicoDetalhado);

	List<ServicoDetalhado> buscarServicosDetalhadosPorTipoServico(Integer id) throws NotFoundException;
	
	ServicoDetalhado atualizarServicoDetalhado(Long id, ServicoDetalhado servicoDetalhadoRequest) throws BusinessException, NotFoundException;

    MensagemRepresentation removerServicoDetalhado(Long id, Long prestadorId) throws BusinessException, NotFoundException;
    
    ServicoDetalhado buscarPorId(Long id) throws NotFoundException;
    
    List<ServicoDetalhado> buscarPorPrestadorId(Long prestadorId) throws NotFoundException;
    
}
