package hyve.petshow.service.port;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.BusinessException;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ServicoDetalhadoService {
	ServicoDetalhado adicionarServicoDetalhado(ServicoDetalhado servicoDetalhado);

    //List<ServicoDetalhado> buscarServicosDetalhadosPorPrestador(Long id);

	ServicoDetalhado atualizarServicoDetalhado(Long id, ServicoDetalhado servicoDetalhadoRequest)throws Exception;

    MensagemRepresentation removerServicoDetalhado(Long id) throws Exception;
    

    
}
