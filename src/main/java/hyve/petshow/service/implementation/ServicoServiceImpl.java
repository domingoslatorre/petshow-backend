package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Servico;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ServicoRepository;
import hyve.petshow.service.port.ServicoService;

@Service
public class ServicoServiceImpl implements ServicoService {

    private static final String MENSAGEM_SUCESSO = "Operação executada com sucesso!";
    private static final String MENSAGEM_FALHA = "Falha durante a execução da operação.";

    @Autowired
    private ServicoRepository repository;

    @Override
    public Servico adicionarServico(Servico servico) {
        return repository.save(servico);
    }
    

    @Override
    public List<Servico> buscarServicos() {
        return repository.findAll();
    }

    //
    @Override
    public Optional<Servico> atualizarServico(Long id, Servico servicoRequest) {
        Optional<Servico> servicoOptional = repository.findById(id);
        Optional<Servico> response = Optional.empty();

        if(servicoOptional.isPresent()){
        	Servico servico = servicoOptional.get();
           
        	servico.setNome(servicoRequest.getNome());
        	servico.setDescricao(servicoRequest.getDescricao());
      	
            response = Optional.of(repository.save(servico));
        }

        return response;
    }

    @Override
    public MensagemRepresentation removerServico(Long id) {
    	repository.deleteById(id);
		MensagemRepresentation mensagem = new MensagemRepresentation();
		mensagem.setId(id);
		mensagem.setSucesso(!repository.existsById(id));
		return mensagem;
    }
}

