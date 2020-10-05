package hyve.petshow.service.port;

import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.ServicoDetalhado;


@Service
public interface ServicoDetalhadoService {
	ServicoDetalhado adicionarServicoDetalhado(ServicoDetalhado servicoDetalhado);

    //List<ServicoDetalhado> findByPrestador(Long id);

	ServicoDetalhado atualizarServicoDetalhado(Long id, ServicoDetalhado servicoDetalhadoRequest) throws Exception;

    MensagemRepresentation removerServicoDetalhado(Long id) throws Exception;
    
    ServicoDetalhado buscarPorId(Long id) throws Exception;
    
    ServicoDetalhado buscarPorIdEPrestador(Long idServico, Long idPrestador) throws Exception;
    
}
