package hyve.petshow.service.port;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Servico;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ServicoService {
	Servico adicionarServico (Servico servico)throws Exception;

    List<Servico> buscarServicos ();
    
    Servico atualizarServico(Integer id, Servico servico) throws Exception;

    MensagemRepresentation removerServico(Integer id) throws Exception;
}
