package hyve.petshow.service.port;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ServicoDetalhadoService {
	List<ServicoDetalhado> criarServico(List<ServicoDetalhado> servicoDetalhado);

    List<ServicoDetalhado> buscaServicosPorPrestador(Long id);

    Optional<ServicoDetalhado> atualizarServicoDetalhado(Long id, ServicoDetalhado servicoDetalhadoRequest);

    MensagemRepresentation removerServicoDetalhado(Long id) throws Exception;
}
