package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Servico;
//import hyve.petshow.controller.representation.ServicoDetalhadoResponseRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.port.ServicoDetalhadoService;

@Service
public class ServicoDetalhadoServiceImpl implements ServicoDetalhadoService {

    private static final String MENSAGEM_SUCESSO = "Operação executada com sucesso!";
    private static final String MENSAGEM_FALHA = "Falha durante a execução da operação.";

    @Autowired
    private ServicoDetalhadoRepository repository;

    //criarServicos
    @Override
    public List<ServicoDetalhado> adicionarServicos(List<ServicoDetalhado> servicosDetalhados) {
        return repository.saveAll(servicosDetalhados);
    }
    

    @Override
    public List<ServicoDetalhado> buscarServicosPorPrestador(Long id) {
        return repository.findByPrestador(id);
    }

    //
    @Override
    public Optional<ServicoDetalhado> atualizarServicoDetalhado(Long id, ServicoDetalhado servicoDetalhadoRequest) {
        Optional<ServicoDetalhado> servicoDetalhadoOptional = repository.findById(id);
        Optional<ServicoDetalhado> response = Optional.empty();

        if(servicoDetalhadoOptional.isPresent()){
        	ServicoDetalhado servicoDetalhado = servicoDetalhadoOptional.get();
           
        	servicoDetalhado.setPreco(servicoDetalhadoRequest.getPreco());
        	servicoDetalhado.setTipo(servicoDetalhadoRequest.getTipo());
        	servicoDetalhado.setAnimaisAceitos(servicoDetalhadoRequest.getAnimaisAceitos());
      	
            response = Optional.of(repository.save(servicoDetalhado));
        }

        return response;
    }

    @Override
    public MensagemRepresentation removerServicoDetalhado(Long id) {
    	repository.deleteById(id);
		MensagemRepresentation mensagem = new MensagemRepresentation();
		mensagem.setId(id);
		mensagem.setSucesso(!repository.existsById(id));
		return mensagem;
    }
}

